package com.npv.ads.ids.inter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterAdmobDebugAdUnit @Inject constructor() : InterAdUnitId {
    override val adUnitId: String
        get() = "ca-app-pub-3940256099942544/1033173712"
}