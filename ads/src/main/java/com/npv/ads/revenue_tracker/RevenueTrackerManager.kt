package com.npv.ads.revenue_tracker

interface RevenueTrackerManager {
    fun <Ad>trackAdRevenue(ad: Ad)
}