package com.npv.alibs.di

import com.npv.ads.banners.models.BannerSetting
import com.npv.ads.banners.provider.IDefaultBannerSettingsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BannerModule {

    @Provides
    @Singleton
    fun provideNativeAdConditions(): IDefaultBannerSettingsProvider {
        return object : IDefaultBannerSettingsProvider {
            override fun getDefaultBannerSettings(): Map<String, BannerSetting> {
                return emptyMap()
            }
        }
    }

}