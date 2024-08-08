package com.npv.ads.admob.natives.provider

import com.npv.ads.admob.natives.models.NativeDisplaySetting

interface IDefaultNativeSettingsProvider {
    fun getDefaultNativeDisplaySettings(): Map<String, com.npv.ads.admob.natives.models.NativeDisplaySetting>?
}