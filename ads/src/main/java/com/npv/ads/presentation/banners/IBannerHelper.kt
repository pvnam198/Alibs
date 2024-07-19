package com.npv.ads.presentation.banners

import android.app.Activity
import android.view.ViewGroup

interface IBannerHelper {

    suspend fun showBannerAd(activity: Activity, viewGroup: ViewGroup, adId: String)

    suspend fun setBannerSettings(json: String)

}