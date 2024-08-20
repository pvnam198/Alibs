package com.npv.ads.ids.native_ad

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeAdmobDebugAdUnit @Inject constructor() : NativeAdUnitId {
    override val adUnitId: String
        get() = "ca-app-pub-3940256099942544/2247696110"
}