package com.npv.ads.admob.banners.models

sealed class CollapsibleType {
    data class BannerSettingDefined(val bannerSettingId: String) : CollapsibleType()
    data class Specific(val collapsible: Boolean) : CollapsibleType()
}