package com.npv.ads.admob.reward_ad.managers

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.npv.ads.admob.reward_ad.repositories.RewardAdRepository
import com.npv.ads.models.MoreCondition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardAdManagerImpl @Inject constructor(
    private val rewardAdRepository: RewardAdRepository
) : RewardAdManager {

    private val lockShow = Any()
    private var isShowing = false
    private var moreCondition: MoreCondition? = null

    override fun load(
        adUnitId: String,
        onSucceed: ((state: RewardedAd) -> Unit)?,
        onFailed: ((msg: String) -> Unit)?
    ) {
        val rewardedAd = rewardAdRepository.rewardedAd
        if (rewardedAd != null) {
            onSucceed?.invoke(rewardedAd)
            return
        }
        if (moreCondition?.shouldLoad() == false) {
            onFailed?.invoke("return load by more condition")
            return
        }
        rewardAdRepository.load(adUnitId, onLoaded = {
            onSucceed?.invoke(it)
        }, onFailed = {
            onFailed?.invoke(it)
        })
    }

    override fun show(
        activity: Activity,
        onDismiss: ((reward: Boolean) -> Unit)?,
        preloadAdUnitId: String?
    ) {
        synchronized(lockShow) {
            if (isShowing) return
            val rewardAd = rewardAdRepository.rewardedAd
            if (rewardAd == null) {
                loadNewAd(preloadAdUnitId ?: return)
                return
            }
            isShowing = true
            var reward = false
            rewardAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    isShowing = false
                    onDismiss?.invoke(reward)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    isShowing = false
                    onDismiss?.invoke(reward)
                }
            }
            rewardAd.show(activity, OnUserEarnedRewardListener {
                reward = true
            })
        }
    }

    override fun setMoreCondition(condition: MoreCondition) {
        moreCondition = condition
    }

    private fun loadNewAd(adUnitId: String) {
        rewardAdRepository.rewardedAd = null
        rewardAdRepository.load(adUnitId)
    }

}