package com.npv.ads.models.banners

sealed class CollapsibleType {
    data class BannerSettingDefined(val bannerSettingId: String) : CollapsibleType()
    data class Specific(val collapsible: Boolean) : CollapsibleType()
}