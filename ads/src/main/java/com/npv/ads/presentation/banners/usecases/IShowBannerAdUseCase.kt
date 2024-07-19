package com.npv.ads.presentation.banners.usecases

import android.view.ViewGroup

interface IShowBannerAdUseCase {

    suspend operator fun invoke(viewGroup: ViewGroup, adId: String)

}