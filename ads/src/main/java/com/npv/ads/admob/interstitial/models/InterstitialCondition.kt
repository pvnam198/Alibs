package com.npv.ads.admob.interstitial.models

interface InterstitialCondition {

    fun shouldLoad(): Boolean

    fun shouldShow(): Boolean

}