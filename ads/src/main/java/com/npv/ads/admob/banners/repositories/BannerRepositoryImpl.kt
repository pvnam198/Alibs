package com.npv.ads.admob.banners.repositories

import com.google.gson.Gson
import com.npv.ads.admob.banners.models.BannerSetting
import com.npv.ads.admob.banners.provider.DefaultBannerSettingsProvider
import com.npv.ads.sharedPref.AdsSharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerRepositoryImpl @Inject constructor(
    private val adsSharedPref: AdsSharedPref
) : BannerRepository {

    private var defaultBannerSettingsProvider: DefaultBannerSettingsProvider? = null

    private var bannerSettings: Array<BannerSetting>? = null

    override fun loadConfig() =
        setBannerSettings(adsSharedPref.getBannerSettings())

    override fun setDefaultBannerSettingsProvider(defaultBannerSettingsProvider: DefaultBannerSettingsProvider) {
        this.defaultBannerSettingsProvider = defaultBannerSettingsProvider
    }

    override fun getBannerSetting(bannerSettingId: String): BannerSetting? {
        if (bannerSettings == null) {
            loadConfig()
        }
        return bannerSettings?.find { it.id == bannerSettingId }
            ?: defaultBannerSettingsProvider?.getDefaultBannerSettings()
                ?.find { it.id == bannerSettingId }
    }

    override fun setBannerSettings(json: String): Job = CoroutineScope(Dispatchers.Main).launch {
        if (json.isEmpty()) return@launch
        bannerSettings = jsonToBannerSettings(json) ?: return@launch
        adsSharedPref.setBannerSettings(json)
    }

    private fun jsonToBannerSettings(json: String): Array<BannerSetting>? {
        return try {
            Gson().fromJson(json, Array<BannerSetting>::class.java)
        } catch (e: Exception) {
            null
        }
    }

}