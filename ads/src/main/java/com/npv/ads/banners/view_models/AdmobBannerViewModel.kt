package com.npv.ads.banners.view_models

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.npv.ads.banners.repositories.IBannerAdRepository
import com.npv.ads.banners.use_case.IShowBannerUseCase
import com.npv.ads.di.BannerModule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdmobBannerViewModel @Inject constructor(
    @BannerModule.AdmobBannerAdRepository private val bannerRepository: IBannerAdRepository,
    @BannerModule.ShowBannerAdmobUseCase private val showBannerUseCase: IShowBannerUseCase
) : ViewModel() {


    fun showOrHideIfNeed(
        viewGroup: ViewGroup,
        adId: String,
        bannerSettingId: String?
    ) {
        showBannerUseCase.showIfNeed(viewGroup, adId, bannerSettingId)
    }

}