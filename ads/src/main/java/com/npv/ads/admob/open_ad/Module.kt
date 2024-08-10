package com.npv.ads.admob.open_ad

import android.content.Context
import com.npv.ads.admob.open_ad.manager.AdOpenAdManager
import com.npv.ads.admob.open_ad.manager.AdOpenAdManagerImpl
import com.npv.ads.admob.open_ad.repositories.AdOpenRepository
import com.npv.ads.admob.open_ad.repositories.AdOpenRepositoryImpl
import com.npv.ads.admob.revenue_tracker.AppOpenAdRevenueTracker
import com.npv.ads.section_loader.createSectionLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideAdOpenRepository(
        @ApplicationContext context: Context,
        tracker: AppOpenAdRevenueTracker
    ): AdOpenRepository {
        return AdOpenRepositoryImpl(
            context = context,
            sessionLoader = createSectionLoader(),
            revenueTracker = tracker
        )
    }

    @Provides
    @Singleton
    fun provideAdOpenManager(
        adOpenRepository: AdOpenRepository
    ): AdOpenAdManager {
        return AdOpenAdManagerImpl(
            adOpenRepository = adOpenRepository
        )
    }

}