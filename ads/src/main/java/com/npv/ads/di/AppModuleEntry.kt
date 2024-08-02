package com.npv.ads.di

import com.npv.ads.natives.use_case.ShouldShowNativeAdUseCase
import com.npv.ads.natives.views.NativeAdViewBinder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppModuleEntry {

    fun getShouldShowNativeAdUseCase(): ShouldShowNativeAdUseCase

    fun getNativeAdViewBinder(): NativeAdViewBinder
}