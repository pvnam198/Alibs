package com.npv.ads.admob.revenue_tracker

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

    @Provides
    @Singleton
    fun provideInterstitialRevenueTracker(): InterstitialRevenueTracker {
        return InterstitialRevenueTrackerImpl()
    }

    @Provides
    @Singleton
    fun provideAppOpenAdRevenueTracker(): AppOpenAdRevenueTracker {
        return AppOpenAdRevenueTrackerImpl()
    }

    @Provides
    @Singleton
    fun provideRewardRevenueTracker(): RewardRevenueTracker {
        return RewardRevenueTrackerImpl()
    }

}