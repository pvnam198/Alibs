package com.npv.alibs.di

import com.npv.ads.admob.natives.models.NativeAdCondition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NativeModule {

    @Provides
    @Singleton
    fun provideNativeAdConditions(): NativeAdCondition {
        return object : NativeAdCondition {
            override fun shouldLoad(): Boolean {
                return true
            }
        }
    }

    @Provides
    @Singleton
    fun provideDefaultNativeSettingsProvider(): com.npv.ads.admob.natives.provider.IDefaultNativeSettingsProvider {
        return object : com.npv.ads.admob.natives.provider.IDefaultNativeSettingsProvider {
            override fun getDefaultNativeDisplaySettings(): Map<String, com.npv.ads.admob.natives.models.NativeDisplaySetting> {
                return emptyMap()
            }
        }
    }
}