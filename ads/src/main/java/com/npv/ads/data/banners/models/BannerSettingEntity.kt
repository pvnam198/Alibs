package com.npv.ads.data.banners.models

import com.google.gson.annotations.SerializedName
import com.npv.ads.domain.banners.models.BannerSetting

data class BannerSettingEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("show")
    val show: Boolean,
    @SerializedName("collapsible")
    val collapsible: Boolean
) {
    fun toBannerSetting() = BannerSetting(id, show, collapsible)
}