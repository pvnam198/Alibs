package com.npv.ads.admob.natives.repositories

import com.npv.ads.admob.natives.listeners.NativeAdChangedListener
import com.npv.ads.admob.natives.models.NativeDisplaySetting

interface NativeAdRepository {

    fun addListener(ls: com.npv.ads.admob.natives.listeners.NativeAdChangedListener)

    fun removeListener(ls: com.npv.ads.admob.natives.listeners.NativeAdChangedListener)

    fun <Ad> getNativeAd(): Ad?

    fun loadIfNeed(id: String)

    fun getNativeDisplaySetting(id: String): com.npv.ads.admob.natives.models.NativeDisplaySetting?

}