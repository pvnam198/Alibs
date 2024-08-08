package com.npv.ads.sharedPref

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdsSharedPrefAppModule {

    @Provides
    @Singleton
    fun provideAdsSharedPref(@ApplicationContext context: Context): AdsSharedPref {
        return AdsSharedPrefImpl(context)
    }

}