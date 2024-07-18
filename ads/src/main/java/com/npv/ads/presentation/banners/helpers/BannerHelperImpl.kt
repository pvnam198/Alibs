package com.npv.ads.presentation.banners.helpers

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.npv.ads.domain.banners.conditions.BannerCondition
import com.npv.ads.domain.banners.repositories.IBannerAdRepository

class BannerHelperImpl(
    private val activity: Activity,
    private val bannerAdRepository: IBannerAdRepository,
    private val bannerCondition: BannerCondition
) : IBannerHelper {
    override suspend fun showIfNeedElseHide(
        viewGroup: ViewGroup,
        adId: String,
        collapsible: Boolean
    ) {
        val bannerSetting = bannerAdRepository.getBannerSetting(adId)
        val isNeedShow = bannerCondition.isNeedShow() && bannerSetting?.show == true
        if (!isNeedShow) {
            viewGroup.visibility = View.GONE
            return
        }
        viewGroup.visibility = View.VISIBLE
        val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = viewGroup.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        val adWidth = (adWidthPixels / density).toInt()
        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        val adView = AdView(activity)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                viewGroup.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                viewGroup.visibility = View.GONE
            }
        }
        viewGroup.addView(adView)
        adView.adUnitId = adId
        adView.setAdSize(adSize)
        val adRequest = if (collapsible) {
            val extras = Bundle()
            extras.putString("collapsible", "bottom")
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        } else {
            AdRequest.Builder()
        }
        adView.loadAd(adRequest.build())
    }
}