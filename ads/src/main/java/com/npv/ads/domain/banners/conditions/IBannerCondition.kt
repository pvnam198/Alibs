package com.npv.ads.domain.banners.conditions

interface IBannerCondition {
    fun shouldShow(): Boolean
}