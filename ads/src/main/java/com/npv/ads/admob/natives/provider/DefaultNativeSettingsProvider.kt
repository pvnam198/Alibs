package com.npv.ads.admob.natives.provider

import com.npv.ads.models.native_ad.AdDisplayConfig

interface DefaultNativeSettingsProvider {
    fun getDefaultNativeDisplaySettings(): List<AdDisplayConfig>
}