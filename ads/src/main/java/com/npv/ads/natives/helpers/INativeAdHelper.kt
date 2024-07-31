package com.npv.ads.natives.helpers

import com.npv.ads.natives.listeners.NativeAdChangedListener

interface INativeAdHelper<T> {

    fun addListener(ls: NativeAdChangedListener)

    fun removeListener(ls: NativeAdChangedListener)

    fun getNativeAd(): T?

    fun shouldShow(nativeDisplaySettingId: String): Boolean

    fun setNativeSettings(json: String)

    fun loadIfNeed(id: String)

}