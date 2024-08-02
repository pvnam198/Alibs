package com.npv.ads.revenue_tracker

import com.google.android.gms.ads.nativead.NativeAd

interface NativeAdRevenueTracker {
    fun trackAdRevenue(nativeAd: NativeAd)
}