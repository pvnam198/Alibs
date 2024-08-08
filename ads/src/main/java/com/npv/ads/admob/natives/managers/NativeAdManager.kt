package com.npv.ads.admob.natives.managers

import com.google.android.gms.ads.nativead.NativeAd

interface NativeAdManager {

    fun load(id: String)

    fun getNativeAd(): NativeAd?

    fun getNativeAd(id: String): NativeAd?

}