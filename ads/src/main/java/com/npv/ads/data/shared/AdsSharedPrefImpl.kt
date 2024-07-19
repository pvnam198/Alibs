package com.npv.ads.data.shared

import android.content.Context
import com.npv.ads.domain.Constant.DEFAULT_NATIVE_PRELOAD_MAX
import com.npv.ads.domain.shared.IAdsSharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsSharedPrefImpl(context: Context) : IAdsSharedPref {

    private val sharedPref =
        context.getSharedPreferences(IAdsSharedPref.FILE_NAME, Context.MODE_PRIVATE)

    private val edit get() = sharedPref.edit()

    override suspend fun getBannerSettingJson(): String {
        return withContext(Dispatchers.IO) {
            sharedPref.getString(IAdsSharedPref.BANNER_SETTING_JSON, "") ?: ""
        }
    }

    override suspend fun setBannerSettingJson(json: String) {
        withContext(Dispatchers.IO) {
            edit.putString(IAdsSharedPref.BANNER_SETTING_JSON, json).commit()
        }
    }

    override suspend fun getNativeSettingJson(): String {
        return withContext(Dispatchers.IO) {
            sharedPref.getString(IAdsSharedPref.NATIVE_SETTING_JSON, "") ?: ""
        }
    }

    override suspend fun setNativeSettingJson(json: String) {
        withContext(Dispatchers.IO) {
            edit.putString(IAdsSharedPref.NATIVE_SETTING_JSON, json).commit()
        }
    }

    override suspend fun getNativePreloadMax(): Int {
        return withContext(Dispatchers.IO) {
            sharedPref.getInt(IAdsSharedPref.NATIVE_PRELOAD_MAX, DEFAULT_NATIVE_PRELOAD_MAX)
        }
    }

    override suspend fun setNativePreloadMax(max: Int) {
        withContext(Dispatchers.IO) { edit.putInt(IAdsSharedPref.NATIVE_PRELOAD_MAX, max).commit() }
    }

}