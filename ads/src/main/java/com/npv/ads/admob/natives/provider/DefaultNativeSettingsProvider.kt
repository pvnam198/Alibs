package com.npv.ads.admob.natives.provider

import com.npv.ads.admob.natives.models.NativeDisplaySetting

interface DefaultNativeSettingsProvider {
    fun getDefaultNativeDisplaySettings(): List<NativeDisplaySetting>
}