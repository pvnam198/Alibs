package com.npv.ads.admob.open_ad.repositories

import com.google.android.gms.ads.appopen.AppOpenAd

interface AdOpenRepository {

    var appOpenAd: AppOpenAd?

    fun load(adUnitId: String)
}