package com.npv.ads.natives.use_case

import com.npv.ads.AdDistributor
import com.npv.ads.di.NativeModule
import com.npv.ads.natives.repositories.NativeAdRepository

class GetNativeAdRepositoryImpl(@NativeModule.AdmobNativeAdRepository private val admobNativeAdRepository: NativeAdRepository) :
    GetNativeAdRepositoryUseCase {
    override fun invoke(adDistributor: AdDistributor): NativeAdRepository? {
        return when (adDistributor) {
            AdDistributor.ADMOB -> admobNativeAdRepository
            AdDistributor.MAX -> null
        }
    }
}