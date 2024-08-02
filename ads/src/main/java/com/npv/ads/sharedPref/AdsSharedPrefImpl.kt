package com.npv.ads.sharedPref

import android.content.Context
import com.npv.ads.Constant.DEFAULT_NATIVE_PRELOAD_MAX

class AdsSharedPrefImpl(context: Context) : IAdsSharedPref {

    private val sharedPref by lazy {
        context.getSharedPreferences(IAdsSharedPref.FILE_NAME, Context.MODE_PRIVATE)
    }

    private val edit get() = sharedPref.edit()

    override fun getBannerSettings(): String {
        return sharedPref.getString(IAdsSharedPref.BANNER_SETTINGS, "") ?: ""
    }

    override fun setBannerSettings(json: String) {
        edit.putString(IAdsSharedPref.BANNER_SETTINGS, json).commit()
    }

    override fun getNativeSettings(): String {
        return sharedPref.getString(IAdsSharedPref.NATIVE_SETTINGS, "") ?: ""
    }

    override fun setNativeSettings(json: String) {
        edit.putString(IAdsSharedPref.NATIVE_SETTINGS, json).apply()
    }

    override fun getNativePreloadMax(): Int {
        return sharedPref.getInt(
            IAdsSharedPref.NATIVE_PRELOAD_MAX,
            DEFAULT_NATIVE_PRELOAD_MAX
        )
    }

    override fun setNativePreloadMax(max: Int) {
        edit.putInt(IAdsSharedPref.NATIVE_PRELOAD_MAX, max).apply()
    }

}