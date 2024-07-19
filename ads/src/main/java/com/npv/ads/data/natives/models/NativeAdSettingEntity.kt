package com.npv.ads.data.natives.models

import com.google.gson.annotations.SerializedName
import com.npv.ads.domain.natives.models.NativeSetting

class NativeAdSettingEntity(
    @SerializedName("preload_max")
    private val preloadMax: Int,
    @SerializedName("native_display_settings")
    private val nativeDisplaySettings: List<NativeDisplaySettingEntity>
) {
    fun toNativeSetting(): NativeSetting {
        return NativeSetting(preloadMax, nativeDisplaySettings.map { it.toNativeDisplaySetting() })
    }
}