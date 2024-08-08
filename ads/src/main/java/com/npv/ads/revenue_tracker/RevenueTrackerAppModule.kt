package com.npv.ads.revenue_tracker

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
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

}