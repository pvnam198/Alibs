package com.npv.ads.ids.banner

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerAdmobDebugAdUnit @Inject constructor() : BannerAdUnitId {
    override val adUnitId: String
        get() = "ca-app-pub-3940256099942544/9214589741"
}