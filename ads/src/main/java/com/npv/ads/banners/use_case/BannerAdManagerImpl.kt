package com.npv.ads.banners.use_case

import android.view.View
import android.view.ViewGroup
import com.npv.ads.AdDistributor
import com.npv.ads.banners.conditions.IBannerCondition
import com.npv.ads.banners.helpers.BannerHelper
import com.npv.ads.banners.repositories.IBannerAdRepository
import com.npv.ads.di.BannerModule

class BannerAdManagerImpl(
    private val bannerCondition: IBannerCondition,
    private val bannerRepository: IBannerAdRepository,
    @BannerModule.AdmobBannerHelper private val bannerHelper: BannerHelper<*>
) : BannerAdManager {

    private val loadingMaps = HashMap<ViewGroup, Boolean>()

    override fun showIfNeed(
        adDistributor: AdDistributor,
        viewGroup: ViewGroup,
        adId: String,
        bannerSettingId: String?
    ) {
        if (loadingMaps[viewGroup] == true) return
        loadingMaps[viewGroup] = true
        bannerRepository.loadConfig()
        val bannerSetting =
            if (bannerSettingId == null) null
            else bannerRepository.getBannerSetting(bannerSettingId)
        val shouldShow = bannerCondition.shouldLoad() && bannerSetting?.show ?: true
        if (!shouldShow) {
            loadingMaps[viewGroup] = false
            viewGroup.visibility = View.GONE
            return
        }
        viewGroup.visibility = View.VISIBLE
        when (adDistributor) {
            AdDistributor.ADMOB -> {
                bannerHelper.loadAndShow(viewGroup, adId, bannerSetting, onLoaded = {
                    viewGroup.visibility = View.VISIBLE
                }, onFailed = {
                    loadingMaps[viewGroup] = false
                    viewGroup.visibility = View.GONE
                })
            }

            else -> Unit
        }
    }
}