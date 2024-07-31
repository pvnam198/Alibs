package com.npv.ads.revenue_tracker

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.google.android.gms.ads.nativead.NativeAd

class AdjustNativeAdTracker : IRevenueTracker<NativeAd> {
    override fun trackAdRevenue(ad: NativeAd) {
        ad.setOnPaidEventListener { adValue ->
            val adRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
            adRevenue.setRevenue(
                (adValue.valueMicros * 1.0 / 1000000.0), adValue.currencyCode
            )
            adRevenue.adRevenueNetwork = ad.responseInfo?.loadedAdapterResponseInfo?.adSourceName
            Adjust.trackAdRevenue(adRevenue)
        }
    }
}