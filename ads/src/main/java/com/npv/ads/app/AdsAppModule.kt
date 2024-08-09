package com.npv.ads.app

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdsAppModule {

    @Provides
    @Singleton
    fun provideAdsManager(@ApplicationContext context: Context): AdsManager {
        return AdsManagerVer1(context)
    }

}