package com.npv.ads.ids.app_open

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppOpenAdmobDebugAdUnit @Inject constructor() : AppOpenAdUnitId {
    override val adUnitId: String
        get() = "ca-app-pub-3940256099942544/9257395921"
}