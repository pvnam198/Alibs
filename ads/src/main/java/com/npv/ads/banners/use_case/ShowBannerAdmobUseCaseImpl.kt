package com.npv.ads.banners.use_case

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowBannerAdmobUseCaseImpl(
    private val context: Context,
    private val bannerCondition: IBannerCondition,
    private val bannerRepository: IBannerAdRepository,
    private val revenueTracker: IRevenueTracker<AdView>
) : IShowBannerUseCase {

    private val loadingMaps = HashMap<ViewGroup, Boolean>()

    override fun showIfNeed(viewGroup: ViewGroup, adId: String, bannerSettingId: String?) {
        if (loadingMaps[viewGroup] == true) return
        loadingMaps[viewGroup] = true
        CoroutineScope(Dispatchers.Main).launch {
            bannerRepository.loadConfig()
            val bannerSetting =
                if (bannerSettingId == null) null
                else bannerRepository.getBannerSetting(bannerSettingId)
            val shouldShow = bannerCondition.shouldLoad() && bannerSetting?.show ?: true
            Log.d("log_debugs", "ShowBannerAdmobUseCaseImpl_showIfNeed shouldShow: $shouldShow")
            if (!shouldShow) {
                loadingMaps[viewGroup] = false
                viewGroup.visibility = View.GONE
                return@launch
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
            revenueTracker.trackAdRevenue(adView)
            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    viewGroup.visibility = View.VISIBLE
                    Log.d("log_debugs", "ShowBannerAdmobUseCaseImpl_showIfNeed onAdLoaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(
                        "log_debugs",
                        "ShowBannerAdmobUseCaseImpl_onAdFailedToLoad adError: $adError"
                    )
                    loadingMaps[viewGroup] = false
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
    }
}