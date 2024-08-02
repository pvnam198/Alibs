package com.npv.ads.sharedPref

interface IAdsSharedPref {

    companion object {

        const val FILE_NAME = "ads_shared_pref"

        const val BANNER_SETTINGS = "com.npv.ads.domain.shared.BANNER_SETTINGS"

        const val NATIVE_SETTINGS = "com.npv.ads.domain.shared.NATIVE_SETTINGS"

        const val NATIVE_PRELOAD_MAX = "com.npv.ads.domain.shared.NATIVE_PRELOAD_MAX"

    }

    fun getBannerSettings(): String

    fun setBannerSettings(json: String)

    fun getNativeSettings(): String

    fun setNativeSettings(json: String)

    fun getNativePreloadMax(): Int

    fun setNativePreloadMax(max: Int)

}