package com.npv.ads.admob.natives.models

import com.google.gson.annotations.SerializedName

data class NativeAdSetting(
    @SerializedName("preload_max")
    val preloadMax: Int,
    @SerializedName("native_display_settings")
    val nativeDisplaySettings: List<NativeDisplaySetting>
)