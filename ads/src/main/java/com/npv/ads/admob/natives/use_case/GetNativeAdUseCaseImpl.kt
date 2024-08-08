package com.npv.ads.admob.natives.use_case

import com.npv.ads.AdDistributor
import com.npv.ads.di.NativeModule
import com.npv.ads.natives.repositories.NativeAdRepository

class GetNativeAdUseCaseImpl(
    @NativeModule.AdmobNativeAdRepository private val admobNativeAdRepository: NativeAdRepository
) : GetNativeAdUseCase {

    override fun <T> invoke(adType: AdDistributor): T? {
        if (adType == AdDistributor.ADMOB) {
            return admobNativeAdRepository.getNativeAd() as? T
        } else if (adType == AdDistributor.MAX) {
            return null
        }
        return null
    }

}