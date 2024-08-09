package com.npv.ads.admob.interstitial.repositories

import android.app.Activity
import com.npv.ads.admob.interstitial.models.InterstitialCondition

interface InterstitialRepository {

    fun load(adUnitId: String)

    fun setInterstitialCondition(interstitialCondition: InterstitialCondition)

    fun show(
        activity: Activity,
        onDismiss: (() -> Unit)? = null,
        preloadAdUnitId: String? = null
    )

    fun setInterShowGap(gap: Long)

}