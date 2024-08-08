package com.npv.ads.admob.banners.provider

import com.npv.ads.admob.banners.models.BannerSetting

interface DefaultBannerSettingsProvider {
    fun getDefaultBannerSettings(): List<BannerSetting>
}