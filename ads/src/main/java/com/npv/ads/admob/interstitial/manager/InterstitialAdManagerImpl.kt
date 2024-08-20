package com.npv.ads.admob.interstitial.manager

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.npv.ads.admob.interstitial.repositories.InterstitialRepository
import com.npv.ads.models.MoreCondition
import com.npv.ads.sharedPref.AdsSharedPref
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdManagerImpl @Inject constructor(
    private val adsSharedPref: AdsSharedPref,
    private val interstitialRepository: InterstitialRepository
) : InterstitialAdManager {

    private val lockShow = Any()
    private var isShowing = false
    private var moreCondition: MoreCondition? = null
    private var lastDismissTime: Long = 0L
    private var _interShowGap: Long
        get() = adsSharedPref.interShowGap
        set(value) {
            adsSharedPref.interShowGap = value
        }

    override fun load(adUnitId: String) {
        if (isAvailable()) return
        if (moreCondition?.shouldLoad() == false) return
        interstitialRepository.load(adUnitId)
    }

    override fun show(activity: Activity, onDismiss: (() -> Unit)?, preloadAdUnitId: String?) {
        synchronized(lockShow) {
            if (isShowing) return

            if (System.currentTimeMillis() - lastDismissTime < _interShowGap) {
                onDismiss?.invoke()
                return
            }

            if (moreCondition?.shouldShow() == false) {
                onDismiss?.invoke()
                return
            }

            showAd(activity, onDismiss, preloadAdUnitId)
        }
    }

    override fun setMoreCondition(moreCondition: MoreCondition) {
        this.moreCondition = moreCondition
    }

    override fun forceShow(activity: Activity, onDismiss: (() -> Unit)?, preloadAdUnitId: String?) {
        synchronized(lockShow) {
            if (isShowing) return
            showAd(activity, onDismiss, preloadAdUnitId)
        }
    }

    private fun showAd(
        activity: Activity,
        onDismiss: (() -> Unit)?,
        preloadAdUnitId: String?
    ) {
        val interstitialAd = interstitialRepository.interstitialAd
        if (interstitialAd == null) {
            preloadAdUnitId?.let { loadNewInterstitial(preloadAdUnitId) }
            onDismiss?.invoke()
            return
        }

        isShowing = true
        preloadAdUnitId?.let { loadNewInterstitial(preloadAdUnitId) }
        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                lastDismissTime = System.currentTimeMillis()
                isShowing = false
                onDismiss?.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                isShowing = false
                onDismiss?.invoke()
            }
        }
        interstitialAd.show(activity)
    }

    override fun isAvailable(): Boolean {
        return interstitialRepository.interstitialAd != null
    }

    override fun setInterShowGap(gap: Long) {
        _interShowGap = gap
    }

    private fun loadNewInterstitial(adUnitId: String) {
        interstitialRepository.interstitialAd = null
        load(adUnitId)
    }

}