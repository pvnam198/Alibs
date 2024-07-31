package com.npv.ads.banners.repositories

import com.google.gson.Gson
import com.npv.ads.banners.models.BannerSetting
import com.npv.ads.sharedPref.IAdsSharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BannerAdRepositoryImpl(
    private val adsSharedPref: IAdsSharedPref,
    private val defaultBannerSettings: Map<String, BannerSetting>? = null
) : IBannerAdRepository {

    private val bannerSettings = HashMap<String, BannerSetting>()

    override suspend fun getBannerSetting(id: String): BannerSetting? {
        return withContext(Dispatchers.Main) {
            bannerSettings[id] ?: defaultBannerSettings?.get(id)
        }
    }

    override suspend fun setBannerSettings(json: String) {
        if (json.isEmpty()) return
        adsSharedPref.setBannerSettings(json)
        collectBannerSettings(json)
    }

    private suspend fun collectBannerSettings(json: String) {
        withContext(Dispatchers.Default) {
            if (json.isEmpty()) return@withContext
            try {
                val bannerConfigs =
                    Gson().fromJson(json, Array<BannerSetting>::class.java)
                bannerConfigs.forEach {
                    bannerSettings[it.id] = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}