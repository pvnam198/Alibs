package com.npv.ads.admob.natives.models

import com.google.gson.annotations.SerializedName

data class NativeSetting(
    @SerializedName("preload_max")
    val preloadMax: Int,
    @SerializedName("native_display_settings")
    val nativeDisplaySettings: List<com.npv.ads.admob.natives.models.NativeDisplaySetting>
) {
    fun getNativeDisplaySettingsMap(): Map<String, com.npv.ads.admob.natives.models.NativeDisplaySetting> {
        val map = HashMap<String, com.npv.ads.admob.natives.models.NativeDisplaySetting>()
        for (nativeDisplaySetting in nativeDisplaySettings) {
            map[nativeDisplaySetting.id] = nativeDisplaySetting
        }
        return map
    }
}
