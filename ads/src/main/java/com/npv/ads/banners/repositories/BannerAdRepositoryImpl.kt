package com.npv.ads.banners.repositories

import com.google.gson.Gson
import com.npv.ads.banners.models.BannerSetting
import com.npv.ads.banners.provider.IDefaultBannerSettingsProvider
import com.npv.ads.sharedPref.IAdsSharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BannerAdRepositoryImpl(
    private val adsSharedPref: IAdsSharedPref,
    private val defaultBannerSettingsProvider: IDefaultBannerSettingsProvider
) : IBannerAdRepository {

    private var bannerSettings = HashMap<String, BannerSetting>()

    override suspend fun loadConfig() =
        collectBannerSettings(withContext(Dispatchers.IO) { adsSharedPref.getBannerSettings() })

    override fun getBannerSetting(id: String): BannerSetting? {
        return bannerSettings[id] ?: defaultBannerSettingsProvider.getDefaultBannerSettings()
            ?.get(id)
    }

    override fun setBannerSettings(json: String) {
        CoroutineScope(Dispatchers.Main).launch {
            if (json.isEmpty()) return@launch
            adsSharedPref.setBannerSettings(json)
            collectBannerSettings(json)
        }
    }

    private fun collectBannerSettings(json: String) {
        try {
            val bannerConfigs =
                Gson().fromJson(json, Array<BannerSetting>::class.java)
            val bannerSettings = HashMap<String, BannerSetting>()
            bannerConfigs.forEach {
                bannerSettings[it.id] = it
            }
            this@BannerAdRepositoryImpl.bannerSettings = bannerSettings
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}