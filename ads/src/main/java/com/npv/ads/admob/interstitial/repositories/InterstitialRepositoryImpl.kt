package com.npv.ads.admob.interstitial.repositories

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.npv.ads.admob.interstitial.models.InterstitialCondition
import com.npv.ads.admob.revenue_tracker.InterstitialRevenueTracker
import com.npv.ads.load_condtions.ConditionLoader
import com.npv.ads.load_condtions.ConditionLoaderAppModule
import com.npv.ads.sharedPref.AdsSharedPref
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @ConditionLoaderAppModule.InterstitialConditionLoader private val conditionLoader: ConditionLoader,
    private val revenueTracker: InterstitialRevenueTracker,
    private val adsSharedPref: AdsSharedPref
) : InterstitialRepository {

    private var interstitialAd: InterstitialAd? = null
    private var interstitialCondition: InterstitialCondition? = null
    private val lockShow = Any()
    private var isShowing = false
    private var lastDismissTime: Long = 0L
    private var _interShowGap: Long
        get() = adsSharedPref.interShowGap
        set(value) {
            adsSharedPref.interShowGap = value
        }

    override fun load(adUnitId: String) {
        Log.d("log_debugs", "InterstitialRepositoryImpl_load: ")
        if (interstitialAd != null) return
        if (interstitialCondition?.shouldLoad() == false) return
        if (!conditionLoader.shouldLoad()) return

        conditionLoader.onLoading()
        InterstitialAd.load(
            context, adUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    conditionLoader.onFailed()
                    Log.d("log_debugs", "InterstitialRepositoryImpl_onAdFailedToLoad: ")
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    conditionLoader.onLoaded()
                    interstitialAd = ad
                    revenueTracker.trackAdRevenue(ad)
                    Log.d("log_debugs", "InterstitialRepositoryImpl_onAdLoaded: ")
                }
            })
    }

    override fun setInterstitialCondition(interstitialCondition: InterstitialCondition) {
        this.interstitialCondition = interstitialCondition
    }

    override fun show(
        activity: Activity,
        onDismiss: (() -> Unit)?,
        preloadAdUnitId: String?
    ) {
        Log.d("log_debugs", "InterstitialRepositoryImpl_show: ")
        synchronized(lockShow) {
            if (isShowing) return

            if (System.currentTimeMillis() - lastDismissTime < _interShowGap) return

            val interstitialAd = this.interstitialAd
            if (interstitialAd == null) {
                load(preloadAdUnitId ?: return)
                onDismiss?.invoke()
                return
            }

            if (interstitialCondition?.shouldShow() == false) {
                onDismiss?.invoke()
                return
            }
            isShowing = true
            interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    Log.d(
                        "log_debugs",
                        "InterstitialRepositoryImpl_onAdDismissedFullScreenContent: "
                    )
                    lastDismissTime = System.currentTimeMillis()
                    isShowing = false
                    onDismiss?.invoke()
                    load(preloadAdUnitId ?: return)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    Log.d(
                        "log_debugs",
                        "InterstitialRepositoryImpl_onAdFailedToShowFullScreenContent: "
                    )
                    isShowing = false
                    onDismiss?.invoke()
                    load(preloadAdUnitId ?: return)
                }
            }
            interstitialAd.show(activity)
        }
    }

    override fun setInterShowGap(gap: Long) {
        this._interShowGap = gap
    }
}