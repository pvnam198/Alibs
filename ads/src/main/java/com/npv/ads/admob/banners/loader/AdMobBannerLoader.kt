package com.npv.ads.admob.banners.loader

import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

interface AdMobBannerLoader {

    fun load(adUnitId: String, adSize: AdSize, collapsible: Boolean, callback: (AdView?) -> Unit)

}