package com.npv.ads.banners.helpers

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
import com.npv.ads.banners.conditions.IBannerCondition
import com.npv.ads.banners.repositories.IBannerAdRepository
import com.npv.ads.revenue_tracker.IRevenueTracker

class AdmobBannerHelperImpl(
    private val context: Context,
    private val bannerAdRepository: IBannerAdRepository,
    private val bannerCondition: IBannerCondition,
    private val revenueTracker: IRevenueTracker<AdView>? = null
) : IBannerHelper {
    override suspend fun showOrHideIfNeed(
        viewGroup: ViewGroup,
        adId: String,
        bannerSettingId: String
    ) {
        val bannerSetting = bannerAdRepository.getBannerSetting(bannerSettingId)
        val shouldShow = bannerCondition.shouldLoad() && bannerSetting?.show == true
        if (!shouldShow) {
            viewGroup.visibility = View.GONE
            return
        }
        viewGroup.visibility = View.VISIBLE
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = viewGroup.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        val adWidth = (adWidthPixels / density).toInt()
        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
        val adView = AdView(context)
        revenueTracker?.trackAdRevenue(adView)
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
        val collapsible = bannerSetting?.collapsible == true
        val adRequest = if (collapsible) {
            val extras = Bundle()
            extras.putString("collapsible", "bottom")
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        } else {
            AdRequest.Builder()
        }
        adView.loadAd(adRequest.build())
    }

    override suspend fun setBannerSettings(json: String) {
        bannerAdRepository.setBannerSettings(json)
    }
}