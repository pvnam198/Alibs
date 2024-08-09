package com.npv.ads.admob.banners.manager

import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.npv.ads.admob.banners.models.BannerCondition
import com.npv.ads.admob.banners.models.CollapsibleType
import com.npv.ads.admob.banners.models.BannerSize
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider

interface AdmobBannerManager {

    fun load(
        adUnitId: String,
        bannerSize: BannerSize? = null,
        type: CollapsibleType? = null,
        callback: ((AdView?) -> Unit)? = null
    )

    fun displayAdIfLoaded(
        adUnitId: String,
        parent: ViewGroup,
        type: CollapsibleType? = null,
        bannerSize: BannerSize? = null,
    )

    fun setBannerCondition(bannerCondition: BannerCondition)

    fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider)

    fun setBannerSettings(json: String)

}