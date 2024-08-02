package com.npv.ads.di

import com.npv.ads.revenue_tracker.AdViewRevenueTracker
import com.npv.ads.revenue_tracker.AdViewRevenueTrackerImpl
import com.npv.ads.revenue_tracker.NativeAdRevenueTracker
import com.npv.ads.revenue_tracker.NativeAdRevenueTrackerImpl
import com.npv.ads.revenue_tracker.RevenueTrackerManager
import com.npv.ads.revenue_tracker.RevenueTrackerManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RevenueTrackerModule {

    @Provides
    @Singleton
    fun provideNativeAdRevenueTracker(): NativeAdRevenueTracker {
        return NativeAdRevenueTrackerImpl()
    }

    @Provides
    @Singleton
    fun provideAdViewRevenueTracker(): AdViewRevenueTracker {
        return AdViewRevenueTrackerImpl()
    }

    @Provides
    @Singleton
    fun provideRevenueTrackerManager(
        adViewRevenueTracker: AdViewRevenueTracker,
        nativeAdRevenueTracker: NativeAdRevenueTracker,
    ): RevenueTrackerManager {
        return RevenueTrackerManagerImpl(adViewRevenueTracker, nativeAdRevenueTracker)
    }


}