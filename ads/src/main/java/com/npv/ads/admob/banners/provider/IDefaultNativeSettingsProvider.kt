package com.npv.ads.admob.banners.provider

import com.npv.ads.models.banners.BannerSetting

interface DefaultBannerSettingsProvider {
    fun getDefaultBannerSettings(): List<BannerSetting>
}