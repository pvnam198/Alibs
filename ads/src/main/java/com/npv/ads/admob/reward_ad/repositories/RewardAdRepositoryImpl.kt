package com.npv.ads.admob.reward_ad.repositories

import android.content.Context
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.npv.ads.admob.revenue_tracker.RewardRevenueTracker
import com.npv.ads.section_loader.SectionLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardAdRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionLoader: SectionLoader,
    private val rewardRevenueTracker: RewardRevenueTracker
) : RewardAdRepository {

    private var lockLoad = Any()
    override var rewardedAd: RewardedAd? = null

    override fun load(
        adUnitId: String,
        onLoaded: ((ad: RewardedAd) -> Unit)?,
        onFailed: ((msg: String) -> Unit)?
    ) {
        synchronized(lockLoad) {
            if (rewardedAd != null) return
            if (!sessionLoader.shouldLoad()) return
            sessionLoader.onLoading()
            RewardedAd.load(context, adUnitId,
                AdManagerAdRequest.Builder().build(),
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        sessionLoader.onFailed()
                        onFailed?.invoke(p0.message)
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        super.onAdLoaded(ad)
                        rewardRevenueTracker.trackAdRevenue(ad)
                        rewardedAd = ad
                        sessionLoader.onLoaded()
                        onLoaded?.invoke(ad)
                    }
                })
        }
    }
}