package com.npv.ads.admob.interstitial.manager

import android.app.Activity
import com.npv.ads.models.MoreCondition

interface InterstitialAdManager {

    fun load(adUnitId: String)

    fun setMoreCondition(moreCondition: MoreCondition)

    fun show(
        activity: Activity,
        onDismiss: (() -> Unit)? = null,
        preloadAdUnitId: String? = null
    )

    fun forceShow(
        activity: Activity,
        onDismiss: (() -> Unit)? = null,
        preloadAdUnitId: String? = null
    )

    fun isAvailable(): Boolean

    fun setInterShowGap(gap: Long)

}