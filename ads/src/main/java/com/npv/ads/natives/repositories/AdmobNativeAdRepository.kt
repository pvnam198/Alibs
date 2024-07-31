package com.npv.ads.natives.repositories

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.natives.conditions.INativeAdConditions
import com.npv.ads.natives.models.NativeDisplaySetting
import com.npv.ads.sharedPref.IAdsSharedPref

class AdmobNativeAdRepository(
    private val context: Context,
    nativeAdConditions: INativeAdConditions,
    adsSharedPref: IAdsSharedPref,
    defaultNativeDisplaySettings: Map<String, NativeDisplaySetting>? = null
) : BaseNativeAdRepository<NativeAd>(
    nativeAdConditions,
    adsSharedPref,
    defaultNativeDisplaySettings
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