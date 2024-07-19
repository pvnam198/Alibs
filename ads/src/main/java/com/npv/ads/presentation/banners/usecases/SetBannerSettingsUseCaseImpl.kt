package com.npv.ads.presentation.banners.usecases

import com.npv.ads.domain.banners.repositories.IBannerAdRepository

class SetBannerSettingsUseCaseImpl(private val bannerAdRepository: IBannerAdRepository) : ISetBannerSettingsUseCase{

    override suspend fun execute(json: String) {
        bannerAdRepository.setBannerSettings(json)
    }

}