package com.npv.ads.admob.revenue_tracker

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.google.android.gms.ads.nativead.NativeAd

class NativeAdRevenueTrackerImpl : NativeAdRevenueTracker {
    override fun trackAdRevenue(nativeAd: NativeAd) {
        nativeAd.setOnPaidEventListener { adValue ->
            val adRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
            adRevenue.setRevenue(
                (adValue.valueMicros * 1.0 / 1000000.0), adValue.currencyCode
            )
            adRevenue.adRevenueNetwork =
                nativeAd.responseInfo?.loadedAdapterResponseInfo?.adSourceName
            Adjust.trackAdRevenue(adRevenue)
        }
    }
}