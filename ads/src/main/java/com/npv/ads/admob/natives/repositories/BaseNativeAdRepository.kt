package com.npv.ads.admob.natives.repositories

import android.util.Log
import com.google.android.gms.ads.nativead.NativeAd
import com.google.gson.Gson
import com.npv.ads.TAG
import com.npv.ads.admob.natives.listeners.NativeAdChangedListener
import com.npv.ads.models.native_ad.NativeSetting
import com.npv.ads.models.native_ad.AdDisplayConfig
import com.npv.ads.admob.natives.provider.DefaultNativeSettingsProvider
import com.npv.ads.section_loader.SectionLoader
import com.npv.ads.admob.revenue_tracker.NativeAdRevenueTracker
import com.npv.ads.models.MoreCondition
import com.npv.ads.sharedPref.AdsSharedPref

abstract class BaseNativeAdRepository(
    private val adsSharedPref: AdsSharedPref,
    private val revenueTracker: NativeAdRevenueTracker,
    private val sectionLoader: SectionLoader
) : NativeAdRepository {

    private var defaultNativeSettingsProvider: DefaultNativeSettingsProvider? = null
    private var adDisplayConfigs: List<AdDisplayConfig>? = null

    private val natives = ArrayList<NativeAd>()
    private val nativeAdsCache = HashMap<String, NativeAd>()

    private val listeners: ArrayList<NativeAdChangedListener> = ArrayList()

    private var moreCondition: MoreCondition? = null

    private val lockLoading = Any()

    private fun loadConfigs() {
        val json = adsSharedPref.getNativeSettings()
        if (json.isEmpty()) return
        val nativeAdSetting = toNativeSetting(json) ?: return
        adDisplayConfigs = nativeAdSetting.adDisplayConfigs
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
        sectionLoader.onLoaded()
        notifyNativeAdLoadingCompleted()
    }

    private fun trackAdRevenue(nativeAds: List<NativeAd>) {
        nativeAds.forEach { revenueTracker.trackAdRevenue(it) }
    }

    protected open fun onAdFailedToLoad(msg: String? = null) {
        sectionLoader.onFailed()
        notifyNativeAdLoadingCompleted()
    }

    override fun load(id: String) {
        synchronized(lockLoading){

            val currentSize = natives.size
            val maxPreload = adsSharedPref.getNativePreloadMax()
            if (currentSize >= maxPreload) {
                Log.d(TAG, "loadIfNeed: return by maxPreload")
                return
            }

            if (moreCondition?.shouldLoad() == false) {
                Log.d(TAG, "loadIfNeed: return by condition")
                return
            }

            if (!sectionLoader.shouldLoad()) {
                Log.d(TAG, "loadIfNeed: return by conditionLoader")
                return
            }

            sectionLoader.onLoading()
            load(id, maxPreload - currentSize)
        }
    }

    override fun getNativeDisplaySetting(id: String): AdDisplayConfig? {
        if (adDisplayConfigs == null) loadConfigs()
        return adDisplayConfigs?.find { it.id == id }
            ?: defaultNativeSettingsProvider?.getDefaultNativeDisplaySettings()
                ?.find { it.id == id }
    }

    override fun setNativeAdSetting(data: String) {
        val nativeAdSetting = toNativeSetting(data) ?: return
        adsSharedPref.setNativeSettings(data)
        adsSharedPref.setNativePreloadMax(nativeAdSetting.preloadMax)
        adDisplayConfigs = nativeAdSetting.adDisplayConfigs
    }

    override fun setDefaultNativeSettingsProvider(defaultNativeSettingsProvider: DefaultNativeSettingsProvider) {
        this.defaultNativeSettingsProvider = defaultNativeSettingsProvider
    }

    private fun toNativeSetting(data: String): NativeSetting? {
        return try {
            Gson().fromJson(data, NativeSetting::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun setMoreCondition(condition: MoreCondition) {
        moreCondition = condition
    }

}