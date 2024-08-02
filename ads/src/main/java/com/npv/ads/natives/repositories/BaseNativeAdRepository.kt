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
import com.npv.ads.natives.provider.IDefaultNativeSettingsProvider
import com.npv.ads.revenue_tracker.RevenueTrackerManager
import com.npv.ads.sharedPref.IAdsSharedPref

abstract class BaseNativeAdRepository<T>(
    private val nativeAdConditions: INativeAdConditions,
    private val adsSharedPref: IAdsSharedPref,
    private val defaultSettingProvider: IDefaultNativeSettingsProvider,
    private val revenueTracker: RevenueTrackerManager
) : NativeAdRepository {
    private var nativeDisplaySettingsMap = mapOf<String, NativeDisplaySetting>()
    private val natives = ArrayList<T>()
    private var isLoading = false
    private var numberFailedLoad = 0
    private var timeLastFailedLoad = 0L
    private var listeners: ArrayList<NativeAdChangedListener> = ArrayList()

    private fun loadLastConfig() {
        val json = adsSharedPref.getNativeSettings()
        if (json.isEmpty()) return
        try {
            val nativeAdSetting =
                Gson().fromJson(json, NativeSetting::class.java)
            this@BaseNativeAdRepository.nativeDisplaySettingsMap =
                nativeAdSetting.getNativeDisplaySettingsMap()
        } catch (e: Exception) {
            e.printStackTrace()
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
        nativeAds.forEach { revenueTracker.trackAdRevenue(it) }
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
        if (isLoading) {
            Log.d(TAG, "loadIfNeed: return by isLoading")
            return
        }
        isLoading = true
        if (!nativeAdConditions.shouldLoad()) {
            isLoading = false
            Log.d(TAG, "loadIfNeed: return by nativeAdConditions.shouldLoad()")
            return
        }

        val currentSize = natives.size
        val maxPreload = adsSharedPref.getNativePreloadMax()

        if (currentSize >= maxPreload) {
            isLoading = false
            Log.d(TAG, "loadIfNeed: return by maxPreload")
            return
        }
        if (isMultipleFailedToLoad()) {
            isLoading = false
            Log.d(TAG, "loadIfNeed: return by isMultipleFailedToLoad")
            return
        }
        Log.d(TAG, "loadIfNeed: load")
        load(id, maxPreload - currentSize)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <Ad> getNativeAd(): Ad? {
        try {
            val nativeAd = natives[0]
            natives.remove(nativeAd)
            return nativeAd as? Ad
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun getNativeDisplaySetting(id: String): NativeDisplaySetting? {
        loadLastConfig()
        return this.nativeDisplaySettingsMap[id]
            ?: defaultSettingProvider.getDefaultNativeDisplaySettings()?.get(id)
    }

}