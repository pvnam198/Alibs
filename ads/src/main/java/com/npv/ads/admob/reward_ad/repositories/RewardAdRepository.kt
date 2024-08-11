package com.npv.ads.admob.reward_ad.repositories

import com.google.android.gms.ads.rewarded.RewardedAd

interface RewardAdRepository {

    var rewardedAd: RewardedAd?

    fun load(
        adUnitId: String,
        onLoaded: ((ad: RewardedAd) -> Unit)? = null,
        onFailed: ((msg: String) -> Unit)? = null
    )

}
