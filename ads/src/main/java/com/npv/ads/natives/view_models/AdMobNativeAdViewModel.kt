package com.npv.ads.natives.view_models

import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.di.NativeModule
import com.npv.ads.di.NativeModule.AdmobNativeAdRepository
import com.npv.ads.natives.repositories.INativeAdRepository
import com.npv.ads.natives.views.INativeAdView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdMobNativeAdViewModel @Inject constructor(
    @AdmobNativeAdRepository private val repo: INativeAdRepository<NativeAd>,
    @NativeModule.AdmobNativeAdView private val nativeAdView: INativeAdView<NativeAd>
) : BaseNativeAdViewModel<NativeAd>(repo, nativeAdView)