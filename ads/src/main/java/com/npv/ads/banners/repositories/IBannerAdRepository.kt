package com.npv.ads.banners.repositories

import com.npv.ads.banners.models.BannerSetting


interface IBannerAdRepository {

    suspend fun getBannerSetting(id: String): BannerSetting?

    suspend fun setBannerSettings(json: String)

}