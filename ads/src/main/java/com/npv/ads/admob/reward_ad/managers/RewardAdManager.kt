package com.npv.ads.admob.reward_ad.managers

import android.app.Activity
import com.google.android.gms.ads.rewarded.RewardedAd
import com.npv.ads.models.MoreCondition

interface RewardAdManager {

    fun load(
        adUnitId: String,
        onSucceed: ((state: RewardedAd) -> Unit)? = null,
        onFailed: ((msg: String) -> Unit)? = null
    )

    fun show(
        activity: Activity,
        onDismiss: ((reward: Boolean) -> Unit)? = null,
        preloadAdUnitId: String? = null
    )

    fun setMoreCondition(condition: MoreCondition)

}