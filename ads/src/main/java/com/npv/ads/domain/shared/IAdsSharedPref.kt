package com.npv.ads.domain.shared

interface IAdsSharedPref {

    companion object {

        const val FILE_NAME = "ads_shared_pref"

        const val BANNER_SETTING_JSON = "banner_setting_json"

    }

    suspend fun getBannerSettingJson(): String

    suspend fun setBannerSettingJson(json: String)

}