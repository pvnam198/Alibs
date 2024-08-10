package com.npv.ads.admob.banners.size_calculator

import com.google.android.gms.ads.AdSize
import com.npv.ads.models.banners.BannerSize

interface AdSizeCalculator {

    fun calculateBannerSize(bannerSize: BannerSize?): AdSize

}