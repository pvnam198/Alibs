package com.npv.ads.presentation.banners.usecases

interface ISetBannerSettingsUseCase {

    suspend fun execute(json: String)

}