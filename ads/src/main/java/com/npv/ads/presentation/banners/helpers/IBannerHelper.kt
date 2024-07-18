package com.npv.ads.presentation.banners.helpers

import android.view.ViewGroup

interface IBannerHelper {
    suspend fun showIfNeedElseHide(
        viewGroup: ViewGroup,
        adId: String,
        collapsible: Boolean
    )

    suspend fun setBannerSettings(json: String)
}