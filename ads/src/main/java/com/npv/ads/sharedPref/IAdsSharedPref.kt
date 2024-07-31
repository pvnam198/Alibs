package com.npv.ads.sharedPref

interface IAdsSharedPref {

    companion object {

        const val FILE_NAME = "ads_shared_pref"

        const val BANNER_SETTINGS = "com.npv.ads.domain.shared.BANNER_SETTINGS"

        const val NATIVE_SETTINGS = "com.npv.ads.domain.shared.NATIVE_SETTINGS"

        const val NATIVE_PRELOAD_MAX = "com.npv.ads.domain.shared.NATIVE_PRELOAD_MAX"

    }

    suspend fun getBannerSettings(): String

    suspend fun setBannerSettings(json: String)

    suspend fun getNativeSettings(): String

    suspend fun setNativeSettings(json: String)

    suspend fun getNativePreloadMax(): Int

    suspend fun setNativePreloadMax(max: Int)

}