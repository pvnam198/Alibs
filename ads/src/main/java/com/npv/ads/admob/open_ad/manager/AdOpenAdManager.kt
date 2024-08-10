package com.npv.ads.admob.open_ad.manager

import android.app.Activity
import com.npv.ads.models.MoreCondition

interface AdOpenAdManager {
    fun load(adUnitId: String)

    fun show(activity: Activity, onDismiss: (() -> Unit)? = null, preloadAdUnitId: String? = null)

    fun setMoreCondition(condition: MoreCondition)
}