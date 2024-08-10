package com.npv.ads.admob.banners.manager

import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.npv.ads.models.banners.CollapsibleType
import com.npv.ads.models.banners.BannerSize
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider
import com.npv.ads.models.MoreCondition

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

    fun setMoreCondition(condition: MoreCondition)

    fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider)

    fun setBannerSettings(json: String)

}