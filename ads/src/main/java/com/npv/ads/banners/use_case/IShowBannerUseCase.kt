package com.npv.ads.banners.use_case

import android.view.ViewGroup

interface IShowBannerUseCase {
    fun showIfNeed(
        viewGroup: ViewGroup,
        adId: String,
        bannerSettingId: String?= null
    )
}