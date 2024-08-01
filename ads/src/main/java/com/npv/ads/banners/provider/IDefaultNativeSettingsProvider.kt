package com.npv.ads.banners.provider

import com.npv.ads.banners.models.BannerSetting

interface IDefaultBannerSettingsProvider {
    fun getDefaultBannerSettings(): Map<String, BannerSetting>?
}