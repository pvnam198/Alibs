package com.npv.ads.banners.use_case

import com.npv.ads.banners.repositories.IBannerAdRepository

class SetBannerSettingsUseCase(
    private val bannerAdRepository: IBannerAdRepository
) {
    operator fun invoke(json: String) {
        bannerAdRepository.setBannerSettings(json)
    }
}