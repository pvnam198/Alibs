package com.npv.ads.sharedPref

import android.content.Context
import com.npv.ads.Constant.DEFAULT_NATIVE_PRELOAD_MAX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsSharedPrefImpl(context: Context) : IAdsSharedPref {

    private val sharedPref by lazy {
        context.getSharedPreferences(IAdsSharedPref.FILE_NAME, Context.MODE_PRIVATE)
    }

    private val edit get() = sharedPref.edit()

    override suspend fun getBannerSettings(): String {
        return sharedPref.getString(IAdsSharedPref.BANNER_SETTINGS, "") ?: ""
    }

    override suspend fun setBannerSettings(json: String) {
        withContext(Dispatchers.IO) {
            edit.putString(IAdsSharedPref.BANNER_SETTINGS, json).commit()
        }
    }

    override suspend fun getNativeSettings(): String {
        return withContext(Dispatchers.IO) {
            sharedPref.getString(IAdsSharedPref.NATIVE_SETTINGS, "") ?: ""
        }
    }

    override suspend fun setNativeSettings(json: String) {
        withContext(Dispatchers.IO) {
            edit.putString(IAdsSharedPref.NATIVE_SETTINGS, json).commit()
        }
    }

    override suspend fun getNativePreloadMax(): Int {
        return withContext(Dispatchers.IO) {
            sharedPref.getInt(
                IAdsSharedPref.NATIVE_PRELOAD_MAX,
                DEFAULT_NATIVE_PRELOAD_MAX
            )
        }
    }

    override suspend fun setNativePreloadMax(max: Int) {
        withContext(Dispatchers.IO) { edit.putInt(IAdsSharedPref.NATIVE_PRELOAD_MAX, max).commit() }
    }

}