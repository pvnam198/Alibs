package com.npv.ads.admob.revenue_tracker

import com.google.android.gms.ads.rewarded.RewardedAd

interface RewardRevenueTracker {
    fun trackAdRevenue(ad: RewardedAd)
}