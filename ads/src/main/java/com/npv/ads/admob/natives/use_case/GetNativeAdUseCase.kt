package com.npv.ads.admob.natives.use_case

import com.npv.ads.AdDistributor

interface GetNativeAdUseCase {
    operator fun <T> invoke(adType: AdDistributor): T?
}