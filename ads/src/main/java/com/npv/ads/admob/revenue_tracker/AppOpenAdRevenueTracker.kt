package com.npv.ads.admob.revenue_tracker

import com.google.android.gms.ads.appopen.AppOpenAd

interface AppOpenAdRevenueTracker {
    fun trackAdRevenue(ad: AppOpenAd)
}