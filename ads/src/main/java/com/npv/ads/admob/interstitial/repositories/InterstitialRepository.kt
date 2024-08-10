package com.npv.ads.admob.interstitial.repositories

import com.google.android.gms.ads.interstitial.InterstitialAd

interface InterstitialRepository {

    var interstitialAd: InterstitialAd?

    fun load(adUnitId: String)

}