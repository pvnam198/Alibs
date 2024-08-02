package com.npv.ads.banners.helpers

import android.view.ViewGroup
import com.npv.ads.banners.models.BannerSetting

interface BannerHelper<T> {
    fun loadAndShow(
        viewGroup: ViewGroup,
        adId: String,
        bannerSetting: BannerSetting?,
        onLoaded: (T) -> Unit,
        onFailed: () -> Unit
    )
}