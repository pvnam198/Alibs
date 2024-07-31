package com.npv.ads.banners.helpers

import android.view.ViewGroup

interface IBannerHelper {

    suspend fun showOrHideIfNeed(viewGroup: ViewGroup, adId: String, bannerSettingId: String)

    suspend fun setBannerSettings(json: String)

}