package com.npv.ads.natives.provider

import com.npv.ads.natives.models.NativeDisplaySetting

interface IDefaultNativeSettingsProvider {
    fun getDefaultNativeDisplaySettings(): Map<String, NativeDisplaySetting>?
}