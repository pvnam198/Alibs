package com.npv.ads.admob.banners.repositories

import com.npv.ads.models.banners.BannerSetting
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider
import kotlinx.coroutines.Job

interface BannerRepository {

    fun getBannerSetting(bannerSettingId: String): BannerSetting?

    fun setBannerSettings(json: String): Job

    fun loadConfig(): Job

    fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider)

}