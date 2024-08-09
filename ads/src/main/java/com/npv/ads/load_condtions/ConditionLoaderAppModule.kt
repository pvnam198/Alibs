package com.npv.ads.load_condtions

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConditionLoaderAppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NativeConditionLoader

    @Provides
    @Singleton
    @NativeConditionLoader
    fun provideNativeConditionLoader(): ConditionLoader {
        return NativeConditionLoaderImpl()
    }

}