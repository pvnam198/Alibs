package com.npv.ads.ids.reward

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardAdmobDebugAdUnit @Inject constructor() : RewardAdUnitId {
    override val adUnitId: String
        get() = "ca-app-pub-3940256099942544/5224354917"
}