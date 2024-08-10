package com.npv.ads.models.native_ad

import com.google.gson.annotations.SerializedName

data class NativeSetting(
    @SerializedName("preload_max")
    val preloadMax: Int,
    @SerializedName("native_display_settings")
    val adDisplayConfigs: List<AdDisplayConfig>
)