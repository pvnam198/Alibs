package com.npv.ads.admob.revenue_tracker

import com.google.android.gms.ads.interstitial.InterstitialAd

interface InterstitialRevenueTracker {
    fun trackAdRevenue(ad: InterstitialAd)
}