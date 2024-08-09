package com.npv.ads.admob.banners.manager

import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.npv.ads.admob.banners.loader.AdMobBannerLoader
import com.npv.ads.admob.banners.models.BannerCondition
import com.npv.ads.admob.banners.models.BannerSize
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider
import com.npv.ads.admob.banners.repositories.BannerRepository
import com.npv.ads.admob.banners.size_calculator.AdSizeCalculator
import com.npv.ads.admob.revenue_tracker.AdViewRevenueTracker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdmobBannerManagerImpl @Inject constructor(
    private val loader: AdMobBannerLoader,
    private val adViewRevenueTracker: AdViewRevenueTracker,
    private val bannerRepository: BannerRepository,
    private val adSizeCalculator: AdSizeCalculator,
) : AdmobBannerManager {

    private var bannerCondition: BannerCondition? = null

    override fun load(
        adUnitId: String,
        bannerSize: BannerSize?,
        bannerSettingId: String?,
        callback: ((AdView?) -> Unit)?
    ) {
        if (bannerCondition?.shouldLoad() == false) {
            callback?.invoke(null)
            return
        }
        val bannerSetting =
            if (bannerSettingId != null) bannerRepository.getBannerSetting(bannerSettingId) else null
        loader.load(
            adUnitId = adUnitId,
            adSize = adSizeCalculator.calculateBannerSize(bannerSize),
            collapsible = bannerSetting?.collapsible ?: true,
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
        bannerSize: BannerSize?,
        bannerSettingId: String?
    ) {
        load(
            adUnitId = adUnitId,
            bannerSize = bannerSize,
            bannerSettingId = bannerSettingId,
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

    override fun setBannerCondition(bannerCondition: BannerCondition) {
        this.bannerCondition = bannerCondition
    }

    override fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider) {
        bannerRepository.setDefaultBannerSettingsProvider(defaultBannerSettingsProvider)
    }

    override fun setBannerSettings(json: String) {
        bannerRepository.setBannerSettings(json)
    }

}