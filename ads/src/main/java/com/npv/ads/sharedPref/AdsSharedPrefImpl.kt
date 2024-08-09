package com.npv.ads.sharedPref

import android.content.Context
import com.npv.ads.Constant.DEFAULT_INTER_SHOW_GAP
import com.npv.ads.Constant.DEFAULT_NATIVE_PRELOAD_MAX
import com.npv.ads.sharedPref.AdsSharedPref.Companion.BANNER_SETTINGS
import com.npv.ads.sharedPref.AdsSharedPref.Companion.FILE_NAME
import com.npv.ads.sharedPref.AdsSharedPref.Companion.INTER_SHOW_GAP
import com.npv.ads.sharedPref.AdsSharedPref.Companion.NATIVE_PRELOAD_MAX
import com.npv.ads.sharedPref.AdsSharedPref.Companion.NATIVE_SETTINGS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsSharedPrefImpl @Inject constructor(@ApplicationContext context: Context) : AdsSharedPref {

    private val sharedPref by lazy {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    private val edit get() = sharedPref.edit()
    
    override var interShowGap: Long
        get() = sharedPref.getLong(INTER_SHOW_GAP, DEFAULT_INTER_SHOW_GAP)
        set(value) {
            edit.putLong(INTER_SHOW_GAP, value).apply()
        }

    override fun getBannerSettings(): String {
        return sharedPref.getString(BANNER_SETTINGS, "") ?: ""
    }

    override suspend fun setBannerSettings(json: String) {
        withContext(Dispatchers.IO) { edit.putString(BANNER_SETTINGS, json).commit() }
    }

    override fun getNativeSettings(): String {
        return sharedPref.getString(NATIVE_SETTINGS, "") ?: ""
    }

    override fun setNativeSettings(json: String) {
        edit.putString(NATIVE_SETTINGS, json).apply()
    }

    override fun getNativePreloadMax(): Int {
        return sharedPref.getInt(
            NATIVE_PRELOAD_MAX,
            DEFAULT_NATIVE_PRELOAD_MAX
        )
    }

    override fun setNativePreloadMax(max: Int) {
        edit.putInt(NATIVE_PRELOAD_MAX, max).apply()
    }

}