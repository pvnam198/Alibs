package com.npv.ads.admob.banners.models

interface BannerCondition {
    fun shouldLoad(): Boolean
}