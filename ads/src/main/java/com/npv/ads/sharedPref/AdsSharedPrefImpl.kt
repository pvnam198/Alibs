package com.npv.ads.sharedPref

import android.content.Context
import com.npv.ads.Constant.DEFAULT_NATIVE_PRELOAD_MAX
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsSharedPrefImpl @Inject constructor(@ApplicationContext context: Context) : AdsSharedPref {

    private val sharedPref by lazy {
        context.getSharedPreferences(AdsSharedPref.FILE_NAME, Context.MODE_PRIVATE)
    }

    private val edit get() = sharedPref.edit()

    override fun getBannerSettings(): String {
        return sharedPref.getString(AdsSharedPref.BANNER_SETTINGS, "") ?: ""
    }

    override suspend fun setBannerSettings(json: String) {
        withContext(Dispatchers.IO) { edit.putString(AdsSharedPref.BANNER_SETTINGS, json).commit() }
    }

    override fun getNativeSettings(): String {
        return sharedPref.getString(AdsSharedPref.NATIVE_SETTINGS, "") ?: ""
    }

    override fun setNativeSettings(json: String) {
        edit.putString(AdsSharedPref.NATIVE_SETTINGS, json).apply()
    }

    override fun getNativePreloadMax(): Int {
        return sharedPref.getInt(
            AdsSharedPref.NATIVE_PRELOAD_MAX,
            DEFAULT_NATIVE_PRELOAD_MAX
        )
    }

    override fun setNativePreloadMax(max: Int) {
        edit.putInt(AdsSharedPref.NATIVE_PRELOAD_MAX, max).apply()
    }

}