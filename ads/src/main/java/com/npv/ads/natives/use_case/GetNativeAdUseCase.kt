package com.npv.ads.natives.use_case

import com.npv.ads.AdDistributor

interface GetNativeAdUseCase {
    operator fun <T> invoke(adType: AdDistributor): T?
}