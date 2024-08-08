package com.npv.ads.admob.natives.use_case

import com.google.gson.Gson
import com.npv.ads.admob.natives.models.NativeSetting
import com.npv.ads.sharedPref.IAdsSharedPref

class SetNativeConfigUseCaseImpl(private val adsSharedPref: IAdsSharedPref) :
    SetNativeConfigUseCase {
    override fun invoke(json: String) {
        try {
            if (json.isEmpty()) return
            adsSharedPref.setNativeSettings(json)
            val nativeSetting = Gson().fromJson(json, com.npv.ads.admob.natives.models.NativeSetting::class.java)
            adsSharedPref.setNativePreloadMax(nativeSetting.preloadMax)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}