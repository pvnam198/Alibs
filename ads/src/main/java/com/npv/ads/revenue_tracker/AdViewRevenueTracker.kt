package com.npv.ads.revenue_tracker

import com.google.android.gms.ads.AdView

interface AdViewRevenueTracker {
    fun trackAdRevenue(adView: AdView)
}