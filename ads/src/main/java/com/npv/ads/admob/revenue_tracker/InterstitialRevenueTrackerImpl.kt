package com.npv.ads.admob.revenue_tracker

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.google.android.gms.ads.interstitial.InterstitialAd
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialRevenueTrackerImpl @Inject constructor() : InterstitialRevenueTracker {
    override fun trackAdRevenue(ad: InterstitialAd) {
        ad.setOnPaidEventListener { adValue ->
            val adRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
            adRevenue.setRevenue(
                (adValue.valueMicros * 1.0 / 1000000.0), adValue.currencyCode
            )
            adRevenue.adRevenueNetwork =
                ad.responseInfo.loadedAdapterResponseInfo?.adSourceName
            Adjust.trackAdRevenue(adRevenue)
        }
    }
}