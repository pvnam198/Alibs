package com.npv.ads.revenue_tracker

import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.nativead.NativeAd

class RevenueTrackerManagerImpl(
    private val adViewRevenueTracker: AdViewRevenueTracker,
    private val nativeAdRevenueTracker: NativeAdRevenueTracker,
) : RevenueTrackerManager {
    override fun <Ad> trackAdRevenue(ad: Ad) {
        when (ad) {
            is AdView -> adViewRevenueTracker.trackAdRevenue(ad)

            is NativeAd -> nativeAdRevenueTracker.trackAdRevenue(ad)
        }
    }
}