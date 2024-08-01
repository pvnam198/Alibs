package com.npv.ads.di

import android.content.Context
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.natives.conditions.INativeAdConditions
import com.npv.ads.natives.provider.IDefaultNativeSettingsProvider
import com.npv.ads.natives.repositories.AdmobNativeAdRepositoryImpl
import com.npv.ads.natives.repositories.INativeAdRepository
import com.npv.ads.natives.views.AdmobNativeAdViewImpl
import com.npv.ads.natives.views.INativeAdView
import com.npv.ads.revenue_tracker.AdjustNativeAdTrackerImpl
import com.npv.ads.revenue_tracker.IRevenueTracker
import com.npv.ads.sharedPref.IAdsSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NativeModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdmobNativeAdView

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdmobNativeAdRepository

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdjustNativeAdTracker

    @AdjustNativeAdTracker
    @Provides
    @Singleton
    fun provideAdjustNativeAdTracker(): IRevenueTracker<NativeAd> {
        return AdjustNativeAdTrackerImpl()
    }

    @AdmobNativeAdRepository
    @Provides
    @Singleton
    fun provideAdmobNativeAdRepository(
        @ApplicationContext context: Context,
        conditions: INativeAdConditions,
        shared: IAdsSharedPref,
        defaultSettingProvider: IDefaultNativeSettingsProvider,
        @AdjustNativeAdTracker revenueTracker: IRevenueTracker<NativeAd>
    ): INativeAdRepository<NativeAd> {
        return AdmobNativeAdRepositoryImpl(
            context,
            conditions,
            shared,
            defaultSettingProvider,
            revenueTracker
        )
    }

    @AdmobNativeAdView
    @Provides
    @Singleton
    fun provideAdmobNativeAdView(@AdmobNativeAdRepository repo: INativeAdRepository<NativeAd>): INativeAdView<NativeAd> {
        return AdmobNativeAdViewImpl(repo)
    }

}