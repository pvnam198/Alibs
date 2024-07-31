package com.npv.ads.natives.repositories

import android.util.Log
import com.google.gson.Gson
import com.npv.ads.Constant.MAX_FAILED_LOAD_TIME
import com.npv.ads.Constant.MIN_FAILED_LOAD_TIME
import com.npv.ads.TAG
import com.npv.ads.natives.conditions.INativeAdConditions
import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.models.NativeDisplaySetting
import com.npv.ads.natives.models.NativeSetting
import com.npv.ads.revenue_tracker.IRevenueTracker
import com.npv.ads.sharedPref.IAdsSharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseNativeAdRepository<T>(
    private val nativeAdConditions: INativeAdConditions,
    private val adsSharedPref: IAdsSharedPref,
    private val defaultNativeDisplaySettings: Map<String, NativeDisplaySetting>? = null,
    private val revenueTracker: IRevenueTracker<T>? = null
) : INativeAdRepository<T> {
    private val nativeDisplaySettingsMap = HashMap<String, NativeDisplaySetting>()
    private val natives = ArrayList<T>()
    private var isLoading = false
    private var numberFailedLoad = 0
    private var timeLastFailedLoad = 0L
    private var listeners: ArrayList<NativeAdChangedListener> = ArrayList()

    init {
        loadLastConfig()
    }

    private fun loadLastConfig() {
        CoroutineScope(Dispatchers.IO).launch {
            val json = adsSharedPref.getNativeSettings()
            if (json.isEmpty()) return@launch
            try {
                val nativeAdSetting =
                    Gson().fromJson(json, NativeSetting::class.java)
                this@BaseNativeAdRepository.nativeDisplaySettingsMap.putAll(
                    nativeAdSetting.getNativeDisplaySettingsMap()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun addListener(ls: NativeAdChangedListener) {
        listeners.add(ls)
    }

    override fun removeListener(ls: NativeAdChangedListener) {
        listeners.remove(ls)
    }

    private fun notifyNativeAdLoadingCompleted() {
        for (ls in listeners) {
            ls.onNativeChanged()
        }
    }

    protected abstract fun load(id: String, preloadMax: Int)

    protected open fun onAdLoaded(nativeAds: List<T>) {
        trackAdRevenue(nativeAds)
        natives.addAll(nativeAds)
        isLoading = false
        numberFailedLoad = 0
        timeLastFailedLoad = 0
        notifyNativeAdLoadingCompleted()
    }

    private fun trackAdRevenue(nativeAds: List<T>) {
        nativeAds.forEach { revenueTracker?.trackAdRevenue(it) }
    }

    protected open fun onAdFailedToLoad(msg: String? = null) {
        isLoading = false
        timeLastFailedLoad = System.currentTimeMillis()
        numberFailedLoad++
        notifyNativeAdLoadingCompleted()
    }

    private fun isMultipleFailedToLoad(): Boolean {
        val lastFailedTimeToCurrentTime = System.currentTimeMillis() - timeLastFailedLoad
        var pendingTime = MIN_FAILED_LOAD_TIME * numberFailedLoad
        if (pendingTime > MAX_FAILED_LOAD_TIME) {
            pendingTime = MAX_FAILED_LOAD_TIME
        }
        return lastFailedTimeToCurrentTime < pendingTime
    }

    override fun loadIfNeed(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            if (!nativeAdConditions.shouldLoad()) {
                Log.d(TAG, "loadIfNeed: return by nativeAdConditions.shouldLoad()")
                return@launch
            }

            val currentSize = natives.size
            val maxPreload = adsSharedPref.getNativePreloadMax()

            if (currentSize >= maxPreload) {
                Log.d(TAG, "loadIfNeed: return by maxPreload")
                return@launch
            }
            if (isMultipleFailedToLoad()) {
                Log.d(TAG, "loadIfNeed: return by isMultipleFailedToLoad")
                return@launch
            }
            if (isLoading) {
                Log.d(TAG, "loadIfNeed: return by isLoading")
                return@launch
            }
            Log.d(TAG, "loadIfNeed: load")
            isLoading = true
            load(id, maxPreload - currentSize)
        }
    }

    override fun getNativeAd(): T? {
        try {
            val nativeAd = natives[0]
            natives.remove(nativeAd)
            return nativeAd
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun getNativeDisplaySetting(id: String): NativeDisplaySetting? {
        return this.nativeDisplaySettingsMap[id] ?: defaultNativeDisplaySettings?.get(id)
    }

    override fun setNativeSettings(json: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (json.isEmpty()) return@launch
                adsSharedPref.setNativeSettings(json)
                val nativeSetting = Gson().fromJson(json, NativeSetting::class.java)
                adsSharedPref.setNativePreloadMax(nativeSetting.preloadMax)
                this@BaseNativeAdRepository.nativeDisplaySettingsMap.putAll(nativeSetting.getNativeDisplaySettingsMap())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}