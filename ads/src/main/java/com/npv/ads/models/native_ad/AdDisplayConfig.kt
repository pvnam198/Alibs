package com.npv.ads.models.native_ad

import com.google.gson.annotations.SerializedName

data class AdDisplayConfig(
    @SerializedName("id") val id: String,
    @SerializedName("show") val shouldShow: Boolean
)