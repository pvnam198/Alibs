package com.npv.ads.admob.banners.di

import android.content.Context
import com.npv.ads.admob.banners.loader.AdMobBannerLoader
import com.npv.ads.admob.banners.loader.AdMobBannerLoaderImpl
import com.npv.ads.admob.banners.manager.AdmobBannerManager
import com.npv.ads.admob.banners.manager.AdmobBannerManagerImpl
import com.npv.ads.admob.banners.repositories.BannerRepository
import com.npv.ads.admob.banners.repositories.BannerRepositoryImpl
import com.npv.ads.admob.banners.size_calculator.AdSizeCalculator
import com.npv.ads.admob.banners.size_calculator.AdSizeCalculatorImpl
import com.npv.ads.admob.revenue_tracker.AdViewRevenueTracker
import com.npv.ads.sharedPref.AdsSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BannerAppModule {

    @Singleton
    @Provides
    fun provideAdMobBannerLoader(
        @ApplicationContext context: Context
    ): AdMobBannerLoader {
        return AdMobBannerLoaderImpl(context)
    }

    @Singleton
    @Provides
    fun provideBannerRepository(
        adsSharedPref: AdsSharedPref
    ): BannerRepository {
        return BannerRepositoryImpl(adsSharedPref)
    }

    @Singleton
    @Provides
    fun provideAdSizeCalculator(
        @ApplicationContext context: Context
    ): AdSizeCalculator {
        return AdSizeCalculatorImpl(context)
    }

    @Singleton
    @Provides
    fun provideAdmobBannerManager(
        adMobBannerLoader: AdMobBannerLoader,
        adViewRevenueTracker: AdViewRevenueTracker,
        bannerRepository: BannerRepository,
        adSizeCalculator: AdSizeCalculator,
    ): AdmobBannerManager {
        return AdmobBannerManagerImpl(
            loader = adMobBannerLoader,
            adViewRevenueTracker = adViewRevenueTracker,
            bannerRepository = bannerRepository,
            adSizeCalculator = adSizeCalculator
        )
    }


}