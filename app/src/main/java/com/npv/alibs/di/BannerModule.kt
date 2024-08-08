package com.npv.alibs.di

import com.npv.ads.admob.banners.models.BannerCondition
import com.npv.ads.admob.banners.models.BannerSetting
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider
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
    fun provideNativeAdConditions(): DefaultBannerSettingsProvider {
        return object : DefaultBannerSettingsProvider {
            override fun getDefaultBannerSettings(): List<BannerSetting> {
                return emptyMap()
            }
        }
    }

    @Provides
    @Singleton
    fun provideBannerCondition(): BannerCondition {
        return object : BannerCondition {
            override fun shouldLoad(): Boolean {
                return true
            }
        }
    }

}