package com.npv.ads.data.natives.repositories

import com.google.gson.Gson
import com.npv.ads.data.natives.models.NativeAdSettingEntity
import com.npv.ads.domain.Constant.MAX_FAILED_LOAD_TIME
import com.npv.ads.domain.Constant.MIN_FAILED_LOAD_TIME
import com.npv.ads.domain.natives.conditions.INativeAdConditions
import com.npv.ads.domain.natives.listeners.NativeAdLoadingCompletedListener
import com.npv.ads.domain.natives.models.NativeDisplaySetting
import com.npv.ads.domain.natives.models.NativeSetting
import com.npv.ads.domain.natives.repositories.INativeAdRepository
import com.npv.ads.domain.shared.IAdsSharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseNativeAdRepository<T>(
    private val nativeAdConditions: INativeAdConditions,
    private val adsSharedPref: IAdsSharedPref,
    private val defaultNativeDisplaySettings: Map<String, NativeDisplaySetting>? = null
) : INativeAdRepository<T> {

    private val natives = ArrayList<T>()
    private var preloadMax = 1
    private var nativeDisplaySettingsMap = HashMap<String, NativeDisplaySetting>()
    private var isLoading = false
    private var numberFailedLoad = 0
    private var timeLastFailedLoad = 0L
    private var nativeAdLoadingCompletedListeners: ArrayList<NativeAdLoadingCompletedListener> = ArrayList()

    override fun addNativeAdLoadingCompletedListener(ls: NativeAdLoadingCompletedListener) {
        nativeAdLoadingCompletedListeners.add(ls)
    }

    override fun removeNativeAdLoadingCompletedListener(ls: NativeAdLoadingCompletedListener) {
        nativeAdLoadingCompletedListeners.remove(ls)
    }

    private fun notifyNativeAdLoadingCompleted() {
        for (ls in nativeAdLoadingCompletedListeners) {
            ls.onNativeAdLoadingCompleted()
        }
    }

    protected abstract fun load(id: String, preloadMax: Int)

    protected open fun setNativeLoaded(nativeAds: List<T>) {
        natives.addAll(nativeAds)
        isLoading = false
        numberFailedLoad = 0
        timeLastFailedLoad = 0
        notifyNativeAdLoadingCompleted()
    }

    private fun isMultipleLoadFailed(): Boolean {
        val lastFailedTimeToCurrentTime = System.currentTimeMillis() - timeLastFailedLoad
        var pendingTime = MIN_FAILED_LOAD_TIME * numberFailedLoad
        if (pendingTime > MAX_FAILED_LOAD_TIME) {
            pendingTime = MIN_FAILED_LOAD_TIME
        }
        return lastFailedTimeToCurrentTime < pendingTime
    }

    protected open fun setNativeLoadFailed() {
        isLoading = false
        timeLastFailedLoad = System.currentTimeMillis()
        numberFailedLoad++
        notifyNativeAdLoadingCompleted()
    }

    override suspend fun loadIfNeed(id: String) {
        if (!nativeAdConditions.shouldLoad()) return
        if (natives.size >= preloadMax) return
        if (isMultipleLoadFailed()) return
        if (isLoading) return
        isLoading = true
        load(id, preloadMax)
    }

    override suspend fun loadDefaultDataConfig() {
        withContext(Dispatchers.IO) {
            val nativeSettingJson = adsSharedPref.getNativeSettingJson()
            if (nativeSettingJson.isEmpty()) return@withContext
            setNativeSettings(nativeSettingJson)
        }
    }

    override suspend fun getNativeAd(): T? {
        try {
            val nativeAd = natives[0]
            natives.remove(nativeAd)
            return nativeAd
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override suspend fun getNativeDisplaySetting(id: String): NativeDisplaySetting? {
        return nativeDisplaySettingsMap[id] ?: defaultNativeDisplaySettings?.get(id)
    }

    override suspend fun setNativeSettings(json: String) {
        withContext(Dispatchers.Default) {
            try {
                if (json.isEmpty()) return@withContext
                adsSharedPref.setNativeSettingJson(json)
                val nativeSettingEntity = Gson().fromJson(json, NativeAdSettingEntity::class.java)
                val nativeSetting: NativeSetting = nativeSettingEntity.toNativeSetting()
                val preloadMax = nativeSetting.preloadMax
                this@BaseNativeAdRepository.preloadMax = preloadMax
                adsSharedPref.setNativePreloadMax(preloadMax)
                nativeDisplaySettingsMap = HashMap()
                nativeDisplaySettingsMap.putAll(nativeSetting.getNativeDisplaySettingsMap())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}