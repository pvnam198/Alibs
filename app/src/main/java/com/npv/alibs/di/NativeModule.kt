package com.npv.alibs.di

import com.npv.ads.natives.conditions.INativeAdConditions
import com.npv.ads.natives.models.NativeDisplaySetting
import com.npv.ads.natives.provider.IDefaultNativeSettingsProvider
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
    fun provideNativeAdConditions(): INativeAdConditions {
        return object : INativeAdConditions {
            override fun shouldLoad(): Boolean {
                return true
            }
        }
    }

    @Provides
    @Singleton
    fun provideDefaultNativeSettingsProvider(): IDefaultNativeSettingsProvider {
        return object : IDefaultNativeSettingsProvider {
            override fun getDefaultNativeDisplaySettings(): Map<String, NativeDisplaySetting> {
                return emptyMap()
            }
        }
    }

}