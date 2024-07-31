package com.npv.ads.natives.repositories

import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.models.NativeDisplaySetting

interface INativeAdRepository<T> {

    fun addListener(ls: NativeAdChangedListener)

    fun removeListener(ls: NativeAdChangedListener)

    fun getNativeAd(): T?

    fun loadIfNeed(id: String)

    fun getNativeDisplaySetting(id: String): NativeDisplaySetting?

    fun setNativeSettings(json: String)

}