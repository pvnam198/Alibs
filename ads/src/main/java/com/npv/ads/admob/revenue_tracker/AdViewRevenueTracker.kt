package com.npv.ads.admob.revenue_tracker

import com.google.android.gms.ads.AdView

interface AdViewRevenueTracker {
    fun trackAdRevenue(adView: AdView)
}