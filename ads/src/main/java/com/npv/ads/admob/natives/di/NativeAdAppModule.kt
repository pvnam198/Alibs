package com.npv.ads.admob.natives.di

import android.content.Context
import com.npv.ads.admob.natives.repositories.NativeAdRepository
import com.npv.ads.admob.natives.repositories.NativeAdRepositoryImpl
import com.npv.ads.revenue_tracker.NativeAdRevenueTracker
import com.npv.ads.sharedPref.AdsSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NativeAdAppModule {

    @Provides
    @Singleton
    fun provideNativeAdRepository(
        @ApplicationContext context: Context,
        shared: AdsSharedPref,
        revenueTracker: NativeAdRevenueTracker
    ): NativeAdRepository {
        return NativeAdRepositoryImpl(
            context = context,
            adsSharedPref = shared,
            nativeAdRevenueTracker = revenueTracker,
        )
    }
}