package com.npv.ads.admob.natives.repositories

import android.util.Log
import com.google.android.gms.ads.nativead.NativeAd
import com.google.gson.Gson
import com.npv.ads.Constant.MAX_FAILED_LOAD_TIME
import com.npv.ads.Constant.MIN_FAILED_LOAD_TIME
import com.npv.ads.TAG
import com.npv.ads.admob.natives.listeners.NativeAdChangedListener
import com.npv.ads.admob.natives.models.NativeAdCondition
import com.npv.ads.admob.natives.models.NativeAdSetting
import com.npv.ads.admob.natives.models.NativeDisplaySetting
import com.npv.ads.admob.natives.provider.DefaultNativeSettingsProvider
import com.npv.ads.revenue_tracker.NativeAdRevenueTracker
import com.npv.ads.sharedPref.AdsSharedPref

abstract class BaseNativeAdRepository(
    private val adsSharedPref: AdsSharedPref,
    private val revenueTracker: NativeAdRevenueTracker
) : NativeAdRepository {

    private var isLoading = false
    private var numberFailedLoad = 0
    private var timeLastFailedLoad = 0L

    private var defaultNativeSettingsProvider: DefaultNativeSettingsProvider? = null
    private var nativeDisplaySettings: List<NativeDisplaySetting>? = null

    private val natives = ArrayList<NativeAd>()
    private val nativeAdsCache = HashMap<String, NativeAd>()

    private val listeners: ArrayList<NativeAdChangedListener> = ArrayList()

    private var nativeAdCondition: NativeAdCondition? = null

    private fun loadConfigs() {
        val json = adsSharedPref.getNativeSettings()
        if (json.isEmpty()) return
        val nativeAdSetting = toNativeSetting(json) ?: return
        nativeDisplaySettings = nativeAdSetting.nativeDisplaySettings
    }

    override fun addListener(ls: NativeAdChangedListener) {
        listeners.add(ls)
    }

    override fun removeListener(ls: NativeAdChangedListener) {
        listeners.remove(ls)
    }

    override fun getNativeAd(): NativeAd? {
        if (natives.size > 0) {
            val nativeAd = natives[0]
            natives.removeAt(0)
            return nativeAd
        }
        return null
    }

    override fun getNativeAd(id: String): NativeAd? {
        val nativeAd = nativeAdsCache[id]
        if (nativeAd != null) {
            return nativeAd
        }
        val newNativeAd = getNativeAd()
        if (newNativeAd != null) {
            nativeAdsCache[id] = newNativeAd
            return newNativeAd
        }
        return null
    }

    override fun releaseNative(id: String) {
        val nativeAd = nativeAdsCache[id] ?: return
        nativeAd.destroy()
        nativeAdsCache.remove(id)
    }

    private fun notifyNativeAdLoadingCompleted() {
        for (ls in listeners) {
            ls.onNativeChanged()
        }
    }

    protected abstract fun load(id: String, preloadMax: Int)

    protected open fun onAdLoaded(nativeAds: List<NativeAd>) {
        trackAdRevenue(nativeAds)
        natives.addAll(nativeAds)
        isLoading = false
        numberFailedLoad = 0
        timeLastFailedLoad = 0
        notifyNativeAdLoadingCompleted()
    }

    private fun trackAdRevenue(nativeAds: List<NativeAd>) {
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

    override fun load(id: String) {
        if (isLoading) {
            Log.d(TAG, "loadIfNeed: return by isLoading")
            return
        }
        isLoading = true
        if (nativeAdCondition?.shouldLoad() == false) {
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

    override fun getNativeDisplaySetting(id: String): NativeDisplaySetting? {
        if (nativeDisplaySettings == null) loadConfigs()
        return nativeDisplaySettings?.find { it.id == id }
            ?: defaultNativeSettingsProvider?.getDefaultNativeDisplaySettings()
                ?.find { it.id == id }
    }

    override fun setNativeAdSetting(data: String) {
        val nativeAdSetting = toNativeSetting(data) ?: return
        adsSharedPref.setNativeSettings(data)
        adsSharedPref.setNativePreloadMax(nativeAdSetting.preloadMax)
        nativeDisplaySettings = nativeAdSetting.nativeDisplaySettings
    }

    override fun setDefaultNativeSettingsProvider(defaultNativeSettingsProvider: DefaultNativeSettingsProvider) {
        this.defaultNativeSettingsProvider = defaultNativeSettingsProvider
    }

    private fun toNativeSetting(data: String): NativeAdSetting? {
        return try {
            Gson().fromJson(data, NativeAdSetting::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun setNativeAdCondition(nativeAdCondition: NativeAdCondition) {
        this.nativeAdCondition = nativeAdCondition
    }

}