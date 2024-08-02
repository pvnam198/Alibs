package com.npv.ads.natives.use_case

import com.npv.ads.AdDistributor
import com.npv.ads.di.NativeModule
import com.npv.ads.natives.models.NativeDisplaySetting
import com.npv.ads.natives.repositories.NativeAdRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShouldShowNativeAdUseCaseImpl @Inject constructor(
    @NativeModule.AdmobNativeAdRepository
    private val admobNativeAdRepository: NativeAdRepository
) : ShouldShowNativeAdUseCase {
    override operator fun invoke(
        adDistributor: AdDistributor,
        nativeDisplaySettingId: String?
    ): Boolean {
        val nativeDisplaySetting: NativeDisplaySetting? = when {
            nativeDisplaySettingId == null -> null
            adDistributor == AdDistributor.ADMOB -> admobNativeAdRepository.getNativeDisplaySetting(
                nativeDisplaySettingId
            )

            adDistributor == AdDistributor.MAX -> null
            else -> null
        }
        if (nativeDisplaySetting == null) return true
        return nativeDisplaySetting.show
    }
}