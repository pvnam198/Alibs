package com.npv.ads.admob.banners.manager

import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.npv.ads.admob.banners.loader.AdMobBannerLoader
import com.npv.ads.models.banners.BannerSize
import com.npv.ads.models.banners.CollapsibleType
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider
import com.npv.ads.admob.banners.repositories.BannerRepository
import com.npv.ads.admob.banners.size_calculator.AdSizeCalculator
import com.npv.ads.admob.revenue_tracker.AdViewRevenueTracker
import com.npv.ads.models.MoreCondition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdmobBannerManagerImpl @Inject constructor(
    private val loader: AdMobBannerLoader,
    private val adViewRevenueTracker: AdViewRevenueTracker,
    private val bannerRepository: BannerRepository,
    private val adSizeCalculator: AdSizeCalculator,
) : AdmobBannerManager {

    private var moreCondition: MoreCondition? = null

    override fun load(
        adUnitId: String,
        bannerSize: BannerSize?,
        type: CollapsibleType?,
        callback: ((AdView?) -> Unit)?
    ) {
        if (moreCondition?.shouldLoad() == false) {
            callback?.invoke(null)
            return
        }
        val collapsible = when (type) {
            is CollapsibleType.BannerSettingDefined -> {
                bannerRepository.getBannerSetting(type.bannerSettingId)?.collapsible ?: true
            }

            is CollapsibleType.Specific -> type.collapsible
            null -> true
        }

        loader.load(
            adUnitId = adUnitId,
            adSize = adSizeCalculator.calculateBannerSize(bannerSize),
            collapsible = collapsible,
            callback = { adView ->
                if (adView != null) {
                    adViewRevenueTracker.trackAdRevenue(adView)
                }
                callback?.invoke(adView)
            }
        )
    }

    override fun displayAdIfLoaded(
        adUnitId: String,
        parent: ViewGroup,
        type: CollapsibleType?,
        bannerSize: BannerSize?
    ) {
        load(
            adUnitId = adUnitId,
            bannerSize = bannerSize,
            type = type,
            callback = {
                if (it != null) {
                    parent.visibility = View.VISIBLE
                    parent.removeAllViews()
                    parent.addView(it)
                } else {
                    parent.visibility = View.GONE
                }
            })
    }

    override fun setMoreCondition(condition: MoreCondition) {
        moreCondition = condition
    }

    override fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider) {
        bannerRepository.setDefaultBannerSettingsProvider(defaultBannerSettingsProvider)
    }

    override fun setBannerSettings(json: String) {
        bannerRepository.setBannerSettings(json)
    }

}