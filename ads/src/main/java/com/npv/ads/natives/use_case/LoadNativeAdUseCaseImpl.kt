package com.npv.ads.natives.use_case

import com.npv.ads.AdDistributor
import com.npv.ads.di.NativeModule
import com.npv.ads.natives.repositories.NativeAdRepository

class LoadNativeAdUseCaseImpl(
    @NativeModule.AdmobNativeAdRepository private val repository: NativeAdRepository
) : LoadNativeAdUseCase {
    override fun load(adType: AdDistributor, id: String) {
        val nativeAdRepository: NativeAdRepository? = when (adType) {
            AdDistributor.ADMOB -> {
                repository
            }

            AdDistributor.MAX -> {
                null
            }
        }
        nativeAdRepository?.loadIfNeed(id)
    }
}