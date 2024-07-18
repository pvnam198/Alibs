package com.npv.ads.domain.banners.repositories

import com.npv.ads.domain.banners.models.BannerSetting

interface IBannerAdRepository {

    suspend fun getBannerSetting(id: String): BannerSetting?

    suspend fun setBannerSettings(json: String)

}