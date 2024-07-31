package com.npv.ads.banners.conditions

interface IBannerCondition {
    fun shouldLoad(): Boolean
}