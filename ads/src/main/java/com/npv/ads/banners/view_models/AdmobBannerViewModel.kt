package com.npv.ads.banners.view_models

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.npv.ads.AdDistributor
import com.npv.ads.banners.use_case.BannerAdManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdmobBannerViewModel @Inject constructor(
    private val bannerAdManager: BannerAdManager
) : ViewModel() {

    fun showIfNeed(
        adDistributor: AdDistributor,
        viewGroup: ViewGroup,
        adId: String,
        bannerSettingId: String?= null
    ) {
        bannerAdManager.showIfNeed(adDistributor, viewGroup, adId, bannerSettingId)
    }

}