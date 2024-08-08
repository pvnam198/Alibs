package com.npv.ads.admob.natives.repositories

import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.admob.natives.listeners.NativeAdChangedListener
import com.npv.ads.admob.natives.models.NativeAdCondition
import com.npv.ads.admob.natives.models.NativeDisplaySetting
import com.npv.ads.admob.natives.provider.DefaultNativeSettingsProvider

interface NativeAdRepository {

    fun setNativeAdSetting(data: String)

    fun addListener(ls: NativeAdChangedListener)

    fun removeListener(ls: NativeAdChangedListener)

    fun getNativeAd(): NativeAd?

    fun getNativeAd(id: String): NativeAd?

    fun releaseNative(id: String)

    fun load(id: String)

    fun getNativeDisplaySetting(id: String): NativeDisplaySetting?

    fun setDefaultNativeSettingsProvider(defaultNativeSettingsProvider: DefaultNativeSettingsProvider)

    fun setNativeAdCondition(nativeAdCondition: NativeAdCondition)

}