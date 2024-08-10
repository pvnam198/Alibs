package com.npv.ads.admob.revenue_tracker

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.nativead.NativeAd
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppOpenAdRevenueTrackerImpl @Inject constructor() : AppOpenAdRevenueTracker {
    override fun trackAdRevenue(ad: AppOpenAd) {
        ad.setOnPaidEventListener { adValue ->
            val adRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
            adRevenue.setRevenue((adValue.valueMicros * 1.0 / 1000000.0), adValue.currencyCode)
            adRevenue.adRevenueNetwork =
                ad.responseInfo.loadedAdapterResponseInfo?.adSourceName
            Adjust.trackAdRevenue(adRevenue)
        }
    }
}