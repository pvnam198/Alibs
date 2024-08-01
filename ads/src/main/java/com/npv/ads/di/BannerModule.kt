package com.npv.ads.di

import android.content.Context
import com.google.android.gms.ads.AdView
import com.npv.ads.banners.conditions.IBannerCondition
import com.npv.ads.banners.provider.IDefaultBannerSettingsProvider
import com.npv.ads.banners.repositories.BannerAdRepositoryImpl
import com.npv.ads.banners.repositories.IBannerAdRepository
import com.npv.ads.banners.use_case.IShowBannerUseCase
import com.npv.ads.banners.use_case.SetBannerSettingsUseCase
import com.npv.ads.banners.use_case.ShowBannerAdmobUseCaseImpl
import com.npv.ads.revenue_tracker.AdjustAdViewTrackerImpl
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
class BannerModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ShowBannerAdmobUseCase

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdjustAdViewTracker

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdmobBannerAdRepository

    @AdjustAdViewTracker
    @Provides
    @Singleton
    fun provideRevenueTracker(): IRevenueTracker<AdView> {
        return AdjustAdViewTrackerImpl()
    }

    @AdmobBannerAdRepository
    @Provides
    @Singleton
    fun provideBannerAdRepository(
        shared: IAdsSharedPref,
        provider: IDefaultBannerSettingsProvider,
    ): IBannerAdRepository {
        return BannerAdRepositoryImpl(shared, provider)
    }

    @ShowBannerAdmobUseCase
    @Provides
    @Singleton
    fun provideShowBannerAdmobUseCase(
        @ApplicationContext context: Context,
        condition: IBannerCondition,
        @AdmobBannerAdRepository repo: IBannerAdRepository,
        @AdjustAdViewTracker revenueTracker: IRevenueTracker<AdView>
    ): IShowBannerUseCase {
        return ShowBannerAdmobUseCaseImpl(context, condition, repo, revenueTracker)
    }

    @Provides
    @Singleton
    fun provideSetBannerSettingsUseCase(
        @AdmobBannerAdRepository repo: IBannerAdRepository,
    ): SetBannerSettingsUseCase {
        return SetBannerSettingsUseCase(repo)
    }

}