package com.npv.ads.admob.natives.views

import com.npv.ads.AdDistributor

interface NativeAdViewBinder {
    fun <Ad> bind(
        adDistributor: AdDistributor,
        templateView: TemplateView<Ad>,
        nativeDisplaySettingId: String? = null
    ): Boolean
}