package com.npv.ads.domain.shared

interface IAdsSharedPref {

    companion object {

        const val FILE_NAME = "ads_shared_pref"

        const val BANNER_SETTING_JSON = "com.npv.ads.domain.shared.BANNER_SETTING_JSON"

        const val NATIVE_SETTING_JSON = "com.npv.ads.domain.shared.NATIVE_SETTING_JSON"

        const val NATIVE_PRELOAD_MAX = "com.npv.ads.domain.shared.NATIVE_PRELOAD_MAX"

    }

    suspend fun getBannerSettingJson(): String

    suspend fun setBannerSettingJson(json: String)

    suspend fun getNativeSettingJson(): String

    suspend fun setNativeSettingJson(json: String)

    suspend fun getNativePreloadMax(): Int

    suspend fun setNativePreloadMax(max: Int)

}