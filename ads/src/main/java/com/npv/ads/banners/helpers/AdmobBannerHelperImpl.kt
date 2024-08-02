package com.npv.ads.banners.helpers

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.npv.ads.banners.models.BannerSetting
import com.npv.ads.revenue_tracker.RevenueTrackerManager

class AdmobBannerHelperImpl(
    private val context: Context,
    private val revenueTracker: RevenueTrackerManager
) : BannerHelper<AdView> {

    override fun loadAndShow(
        viewGroup: ViewGroup,
        adId: String,
        bannerSetting: BannerSetting?,
        onLoaded: (AdView) -> Unit,
        onFailed: () -> Unit
    ) {
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
        revenueTracker.trackAdRevenue(adView)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                onLoaded(adView)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                onFailed()
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

}