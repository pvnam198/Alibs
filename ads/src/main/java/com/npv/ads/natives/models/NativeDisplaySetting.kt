package com.npv.ads.natives.models

import com.google.gson.annotations.SerializedName

class NativeDisplaySetting(
    @SerializedName("id") val id: String,
    @SerializedName("show") val show: Boolean
)