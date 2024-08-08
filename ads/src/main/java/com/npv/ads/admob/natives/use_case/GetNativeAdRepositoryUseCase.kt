package com.npv.ads.admob.natives.use_case

import com.npv.ads.AdDistributor
import com.npv.ads.natives.repositories.NativeAdRepository

interface GetNativeAdRepositoryUseCase {
    operator fun invoke(adDistributor: AdDistributor): NativeAdRepository?
}