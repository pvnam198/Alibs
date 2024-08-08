package com.npv.ads.admob.banners.models

import android.view.ViewGroup

sealed class BannerSize {
    data class FitParent(val parent: ViewGroup) : BannerSize()
    data class Custom(val width: Int, val height: Int) : BannerSize()
}