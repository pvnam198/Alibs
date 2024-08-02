package com.npv.ads.banners.use_case

import android.view.ViewGroup
import com.npv.ads.AdDistributor

interface BannerAdManager {
    fun showIfNeed(
        adDistributor: AdDistributor,
        viewGroup: ViewGroup,
        adId: String,
        bannerSettingId: String?= null
    )
}