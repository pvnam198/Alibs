package com.npv.ads.natives.use_case

import com.npv.ads.AdDistributor

interface ShouldShowNativeAdUseCase {
    operator fun invoke(adDistributor: AdDistributor, nativeDisplaySettingId: String? = null): Boolean
}