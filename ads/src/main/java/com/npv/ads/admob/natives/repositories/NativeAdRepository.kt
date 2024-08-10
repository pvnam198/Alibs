package com.npv.ads.admob.natives.repositories

import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.admob.natives.listeners.NativeAdChangedListener
import com.npv.ads.models.native_ad.AdDisplayConfig
import com.npv.ads.admob.natives.provider.DefaultNativeSettingsProvider
import com.npv.ads.models.MoreCondition

interface NativeAdRepository {

    fun setNativeAdSetting(data: String)

    fun addListener(ls: NativeAdChangedListener)

    fun removeListener(ls: NativeAdChangedListener)

    fun getNativeAd(): NativeAd?

    fun getNativeAd(id: String): NativeAd?

    fun releaseNative(id: String)

    fun load(id: String)

    fun getNativeDisplaySetting(id: String): AdDisplayConfig?

    fun setDefaultNativeSettingsProvider(defaultNativeSettingsProvider: DefaultNativeSettingsProvider)

    fun setMoreCondition(condition: MoreCondition)

}