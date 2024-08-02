package com.npv.ads.natives.models

import com.google.gson.annotations.SerializedName

data class NativeSetting(
    @SerializedName("preload_max")
    val preloadMax: Int,
    @SerializedName("native_display_settings")
    val nativeDisplaySettings: List<NativeDisplaySetting>
) {
    fun getNativeDisplaySettingsMap(): Map<String, NativeDisplaySetting> {
        val map = HashMap<String, NativeDisplaySetting>()
        for (nativeDisplaySetting in nativeDisplaySettings) {
            map[nativeDisplaySetting.id] = nativeDisplaySetting
        }
        return map
    }
}
