package com.npv.ads.natives.repositories

import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.models.NativeDisplaySetting

interface NativeAdRepository {

    fun addListener(ls: NativeAdChangedListener)

    fun removeListener(ls: NativeAdChangedListener)

    fun <Ad> getNativeAd(): Ad?

    fun loadIfNeed(id: String)

    fun getNativeDisplaySetting(id: String): NativeDisplaySetting?

}