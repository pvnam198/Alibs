package com.npv.ads.di

import android.content.Context
import com.npv.ads.sharedPref.AdsSharedPrefImpl
import com.npv.ads.sharedPref.IAdsSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedModule {

    @Provides
    @Singleton
    fun provideIAdsSharedPref(
        @ApplicationContext context: Context,
    ): IAdsSharedPref {
        return AdsSharedPrefImpl(context)
    }

}