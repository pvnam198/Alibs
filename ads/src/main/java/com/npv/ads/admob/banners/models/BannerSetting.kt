package com.npv.ads.admob.banners.models

import com.google.gson.annotations.SerializedName

data class BannerSetting(
    @SerializedName("id")
    val id: String,
    @SerializedName("show")
    val show: Boolean,
    @SerializedName("collapsible")
    val collapsible: Boolean
)