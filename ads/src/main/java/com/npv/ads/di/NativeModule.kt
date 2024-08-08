package com.npv.ads.di

import android.content.Context
import com.npv.ads.admob.natives.models.NativeAdCondition
import com.npv.ads.natives.repositories.NativeAdRepository
import com.npv.ads.natives.use_case.GetNativeAdRepositoryImpl
import com.npv.ads.natives.use_case.GetNativeAdRepositoryUseCase
import com.npv.ads.natives.use_case.GetNativeAdUseCase
import com.npv.ads.natives.use_case.GetNativeAdUseCaseImpl
import com.npv.ads.natives.use_case.LoadNativeAdUseCase
import com.npv.ads.natives.use_case.LoadNativeAdUseCaseImpl
import com.npv.ads.natives.use_case.SetNativeConfigUseCase
import com.npv.ads.natives.use_case.SetNativeConfigUseCaseImpl
import com.npv.ads.natives.use_case.ShouldShowNativeAdUseCase
import com.npv.ads.natives.use_case.ShouldShowNativeAdUseCaseImpl
import com.npv.ads.natives.views.NativeAdViewBinder
import com.npv.ads.natives.views.NativeAdViewImpl
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
class NativeModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdmobNativeAdRepository

    @AdmobNativeAdRepository
    @Provides
    @Singleton
    fun provideAdmobNativeAdRepository(
        @ApplicationContext context: Context,
        conditions: NativeAdCondition,
        shared: IAdsSharedPref,
        defaultSettingProvider: com.npv.ads.admob.natives.provider.IDefaultNativeSettingsProvider,
        revenueTracker: RevenueTrackerManager
    ): NativeAdRepository {
        return com.npv.ads.admob.natives.repositories.AdmobNativeAdRepositoryImpl(
            context,
            conditions,
            shared,
            defaultSettingProvider,
            revenueTracker
        )
    }

    @Provides
    @Singleton
    fun provideShouldShowNativeAdUseCase(@AdmobNativeAdRepository repository: NativeAdRepository): ShouldShowNativeAdUseCase {
        return ShouldShowNativeAdUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideLoadNativeAdUseCase(@AdmobNativeAdRepository repository: NativeAdRepository): LoadNativeAdUseCase {
        return LoadNativeAdUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetNativeAdUseCase(@AdmobNativeAdRepository repository: NativeAdRepository): GetNativeAdUseCase {
        return GetNativeAdUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideAdmobNativeAdView(
        shouldShowNativeAdUseCase: ShouldShowNativeAdUseCase,
        getNativeAdUseCase: GetNativeAdUseCase
    ): NativeAdViewBinder {
        return NativeAdViewImpl(shouldShowNativeAdUseCase, getNativeAdUseCase)
    }

    @Provides
    @Singleton
    fun provideGetNativeAdRepository(
        @AdmobNativeAdRepository repository: NativeAdRepository
    ): GetNativeAdRepositoryUseCase {
        return GetNativeAdRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSetNativeConfigUseCase(
        shared: IAdsSharedPref
    ): SetNativeConfigUseCase {
        return SetNativeConfigUseCaseImpl(shared)
    }

}