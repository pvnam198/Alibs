package com.npv.ads.admob.interstitial.di

import android.content.Context
import com.npv.ads.admob.interstitial.repositories.InterstitialRepository
import com.npv.ads.admob.interstitial.repositories.InterstitialRepositoryImpl
import com.npv.ads.admob.revenue_tracker.InterstitialRevenueTracker
import com.npv.ads.load_condtions.ConditionLoader
import com.npv.ads.load_condtions.ConditionLoaderAppModule
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
        @ConditionLoaderAppModule.InterstitialConditionLoader conditionLoader: ConditionLoader,
        revenueTracker: InterstitialRevenueTracker,
        adsSharedPref: AdsSharedPref
    ): InterstitialRepository {
        return InterstitialRepositoryImpl(
            context = context,
            conditionLoader = conditionLoader,
            revenueTracker = revenueTracker,
            adsSharedPref = adsSharedPref
        )
    }

}