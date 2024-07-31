package com.npv.ads.banners.repositories

import com.npv.ads.banners.models.BannerSetting


interface IBannerAdRepository {

    fun loadConfig()

    fun getBannerSetting(id: String): BannerSetting?

    fun setBannerSettings(json: String)

}