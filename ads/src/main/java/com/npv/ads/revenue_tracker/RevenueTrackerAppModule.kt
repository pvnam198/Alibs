package com.npv.ads.revenue_tracker

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@InstallIn(SingletonComponent::class)
class RevenueTrackerAppModule {

    @Provides
    @Singleton
    fun provideAdViewRevenueTracker(): AdViewRevenueTracker {
        return AdViewRevenueTrackerImpl()
    }

    @Provides
    @Singleton
    fun provideNativeAdRevenueTracker(): NativeAdRevenueTracker {
        return NativeAdRevenueTrackerImpl()
    }

    @Singleton
    @Provides
    fun provideRevenueTrackerManager(
        adViewRevenueTracker: AdViewRevenueTracker,
        nativeAdRevenueTracker: NativeAdRevenueTracker,
    ): RevenueTrackerManager {
        return RevenueTrackerManagerImpl(adViewRevenueTracker, nativeAdRevenueTracker)
    }

}