package com.npv.ads.natives.views

import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.natives.repositories.INativeAdRepository

class AdmobNativeAdViewImpl(
    repository: INativeAdRepository<NativeAd>
) : BaseNativeAdView<NativeAd>(repository)