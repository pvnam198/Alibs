package com.npv.ads.natives.views

import com.npv.ads.AdDistributor

interface NativeAdViewBinder {
    fun <Ad> bind(
        adDistributor: AdDistributor,
        templateView: ITemplateView<Ad>,
        nativeDisplaySettingId: String? = null
    ): Boolean
}