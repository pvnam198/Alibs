package com.npv.ads.admob.natives.repositories

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.load_condtions.ConditionLoader
import com.npv.ads.load_condtions.ConditionLoaderAppModule
import com.npv.ads.revenue_tracker.NativeAdRevenueTracker
import com.npv.ads.sharedPref.AdsSharedPref
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeAdRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    adsSharedPref: AdsSharedPref,
    nativeAdRevenueTracker: NativeAdRevenueTracker,
    @ConditionLoaderAppModule.NativeConditionLoader conditionLoader: ConditionLoader
) : BaseNativeAdRepository(
    adsSharedPref = adsSharedPref,
    revenueTracker = nativeAdRevenueTracker,
    conditionLoader = conditionLoader
) {
    override fun load(id: String, preloadMax: Int) {
        val natives = ArrayList<NativeAd>()
        val adLoader = AdLoader.Builder(context, id).forNativeAd { ad: NativeAd ->
            natives.add(ad)
        }.withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d("log_debugs", "NativeAdRepositoryImpl_onAdLoaded: ")
                onAdLoaded(natives)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("log_debugs", "NativeAdRepositoryImpl_onAdFailedToLoad: ${adError.message}")
                onAdFailedToLoad(adError.message)
            }

        }).build()
        adLoader.loadAds(AdRequest.Builder().build(), preloadMax)
    }
}