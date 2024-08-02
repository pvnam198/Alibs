package com.npv.ads.di

import android.content.Context
import com.npv.ads.banners.conditions.IBannerCondition
import com.npv.ads.banners.helpers.AdmobBannerHelperImpl
import com.npv.ads.banners.helpers.BannerHelper
import com.npv.ads.banners.provider.IDefaultBannerSettingsProvider
import com.npv.ads.banners.repositories.BannerAdRepositoryImpl
import com.npv.ads.banners.repositories.IBannerAdRepository
import com.npv.ads.banners.use_case.BannerAdManager
import com.npv.ads.banners.use_case.BannerAdManagerImpl
import com.npv.ads.revenue_tracker.RevenueTrackerManager
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
class BannerModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ShowBannerAdmobUseCase

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdmobBannerHelper

    @Provides
    @Singleton
    fun provideBannerAdRepository(
        shared: IAdsSharedPref,
        provider: IDefaultBannerSettingsProvider,
    ): IBannerAdRepository {
        return BannerAdRepositoryImpl(shared, provider)
    }

    @Provides
    @Singleton
    @AdmobBannerHelper
    fun provideBannerHelper(
        @ApplicationContext context: Context,
        revenueTracker: RevenueTrackerManager
    ): BannerHelper<*> {
        return AdmobBannerHelperImpl(context, revenueTracker)
    }

    @Provides
    @Singleton
    fun provideAdmobBannerAdManager(
        @ApplicationContext context: Context,
        condition: IBannerCondition,
        repo: IBannerAdRepository,
        @AdmobBannerHelper admobBannerHelper: BannerHelper<*>
    ): BannerAdManager {
        return BannerAdManagerImpl(condition, repo, admobBannerHelper)
    }

}