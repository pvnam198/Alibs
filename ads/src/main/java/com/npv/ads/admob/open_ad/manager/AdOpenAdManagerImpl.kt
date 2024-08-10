package com.npv.ads.admob.open_ad.manager

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.npv.ads.admob.open_ad.repositories.AdOpenRepository
import com.npv.ads.models.MoreCondition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdOpenAdManagerImpl @Inject constructor(
    private val adOpenRepository: AdOpenRepository
) : AdOpenAdManager {

    private val lockShow = Any()
    private var isShowing = false
    private var moreCondition: MoreCondition? = null

    override fun load(adUnitId: String) {
        if (adOpenRepository.appOpenAd != null) return
        if (moreCondition?.shouldLoad() == false) return
        adOpenRepository.load(adUnitId)
    }

    override fun show(activity: Activity, onDismiss: (() -> Unit)?, preloadAdUnitId: String?) {
        synchronized(lockShow) {
            if (isShowing) return
            val adOpenAd = adOpenRepository.appOpenAd
            if (adOpenAd == null) {
                onDismiss?.invoke()
                adOpenRepository.load(preloadAdUnitId ?: return)
                return
            }
            isShowing = true
            loadNewAd(preloadAdUnitId ?: return)
            adOpenAd.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        onDismiss?.invoke()
                        isShowing = false
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        onDismiss?.invoke()
                        isShowing = false
                    }
                }
            adOpenAd.show(activity)
        }
    }

    override fun setMoreCondition(condition: MoreCondition) {
        moreCondition = condition
    }

    private fun loadNewAd(adUnitId: String) {
        adOpenRepository.appOpenAd = null
        load(adUnitId)
    }

}