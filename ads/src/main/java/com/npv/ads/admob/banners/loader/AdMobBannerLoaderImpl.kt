package com.npv.ads.admob.banners.loader

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.npv.ads.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdMobBannerLoaderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AdMobBannerLoader {

    override fun load(
        adUnitId: String,
        adSize: AdSize,
        collapsible: Boolean,
        callback: (AdView?) -> Unit
    ) {
        Log.d("log_debugs", "AdMobBannerLoaderImpl_load: $collapsible")
        val adView = AdView(context)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                callback.invoke(adView)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                callback.invoke(null)
            }
        }
        adView.adUnitId = adUnitId
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