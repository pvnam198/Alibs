package com.npv.ads.domain.natives.repositories

import com.npv.ads.domain.natives.listeners.NativeAdLoadingCompletedListener
import com.npv.ads.domain.natives.models.NativeDisplaySetting

interface INativeAdRepository<T> {

    fun addNativeAdLoadingCompletedListener(ls: NativeAdLoadingCompletedListener)

    fun removeNativeAdLoadingCompletedListener(ls: NativeAdLoadingCompletedListener)

    suspend fun loadDefaultDataConfig()

    suspend fun getNativeAd(): T?

    suspend fun loadIfNeed(id: String)

    suspend fun getNativeDisplaySetting(id: String): NativeDisplaySetting?

    suspend fun setNativeSettings(json: String)

}