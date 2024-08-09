package com.npv.ads.admob.banners.manager

import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.npv.ads.admob.banners.models.BannerCondition
import com.npv.ads.admob.banners.models.BannerSize
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider

interface AdmobBannerManager {

    fun load(
        adUnitId: String,
        bannerSize: BannerSize? = null,
        bannerSettingId: String? = null,
        callback: ((AdView?) -> Unit)? = null
    )

    fun load(
        adUnitId: String,
        bannerSize: BannerSize? = null,
        collapsible: Boolean = false,
        callback: ((AdView?) -> Unit)? = null
    )

    fun displayAdIfLoaded(
        adUnitId: String,
        parent: ViewGroup,
        bannerSize: BannerSize? = null,
        bannerSettingId: String? = null,
    )

    fun displayAdIfLoaded(
        adUnitId: String,
        parent: ViewGroup,
        bannerSize: BannerSize? = null,
        collapsible: Boolean = false,
    )

    fun setBannerCondition(bannerCondition: BannerCondition)

    fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider)

    fun setBannerSettings(json: String)

}