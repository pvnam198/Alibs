package com.npv.ads.admob.natives.repositories

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.admob.natives.models.NativeAdCondition
import com.npv.ads.revenue_tracker.RevenueTrackerManager
import com.npv.ads.sharedPref.IAdsSharedPref

class AdmobNativeAdRepositoryImpl(
    private val context: Context,
    nativeAdConditions: NativeAdCondition,
    adsSharedPref: IAdsSharedPref,
    defaultSettingProvider: com.npv.ads.admob.natives.provider.IDefaultNativeSettingsProvider,
    revenueTracker: RevenueTrackerManager
) : com.npv.ads.admob.natives.repositories.BaseNativeAdRepository<NativeAd>(
    nativeAdConditions,
    adsSharedPref,
    defaultSettingProvider,
    revenueTracker
) {
    override fun load(id: String, preloadMax: Int) {
        val natives = ArrayList<NativeAd>()
        val adLoader = AdLoader.Builder(context, id).forNativeAd { ad: NativeAd ->
            natives.add(ad)
        }.withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                onAdLoaded(natives)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                onAdFailedToLoad(adError.message)
            }
        }).build()
        adLoader.loadAds(AdRequest.Builder().build(), preloadMax)
    }
}