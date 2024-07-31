package com.npv.ads.revenue_tracker

interface IRevenueTracker<Ad> {
    fun trackAdRevenue(ad: Ad)
}