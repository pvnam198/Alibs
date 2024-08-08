package com.npv.ads.admob.natives.use_case

import android.util.Log
import com.npv.ads.AdDistributor
import com.npv.ads.di.NativeModule
import com.npv.ads.admob.natives.models.NativeDisplaySetting
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
        Log.d("log_debugs", "ShouldShowNativeAdUseCaseImpl_invoke: $nativeDisplaySettingId")
        val nativeDisplaySetting: com.npv.ads.admob.natives.models.NativeDisplaySetting? = when {
            nativeDisplaySettingId == null -> null
            adDistributor == AdDistributor.ADMOB -> admobNativeAdRepository.getNativeDisplaySetting(
                nativeDisplaySettingId
            )

            adDistributor == AdDistributor.MAX -> null
            else -> null
        }
        Log.d("log_debugs", "ShouldShowNativeAdUseCaseImpl_invoke nativeDisplaySetting: $nativeDisplaySetting")
        if (nativeDisplaySetting == null) return true
        return nativeDisplaySetting.show
    }
}