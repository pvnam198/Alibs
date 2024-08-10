package com.npv.ads.admob.interstitial.di

import android.content.Context
import com.npv.ads.admob.interstitial.manager.InterstitialAdManager
import com.npv.ads.admob.interstitial.manager.InterstitialAdManagerImpl
import com.npv.ads.admob.interstitial.repositories.InterstitialRepository
import com.npv.ads.admob.interstitial.repositories.InterstitialRepositoryImpl
import com.npv.ads.admob.revenue_tracker.InterstitialRevenueTracker
import com.npv.ads.section_loader.createSectionLoader
import com.npv.ads.sharedPref.AdsSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideInterstitialRepository(
        @ApplicationContext context: Context,
        revenueTracker: InterstitialRevenueTracker,
    ): InterstitialRepository {
        return InterstitialRepositoryImpl(
            context = context,
            sectionLoader = createSectionLoader(),
            revenueTracker = revenueTracker
        )
    }

    @Provides
    @Singleton
    fun provideInterstitialManager(
        adsSharedPref: AdsSharedPref,
        interstitialRepository: InterstitialRepository,
    ): InterstitialAdManager {
        return InterstitialAdManagerImpl(
            adsSharedPref = adsSharedPref,
            interstitialRepository = interstitialRepository
        )
    }

}