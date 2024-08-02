package com.npv.ads.natives.use_case

import com.npv.ads.AdDistributor

interface LoadNativeAdUseCase {
    fun load(adType: AdDistributor, id: String)
}