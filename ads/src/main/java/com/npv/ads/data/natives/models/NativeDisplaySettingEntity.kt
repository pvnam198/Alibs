package com.npv.ads.data.natives.models

import com.google.gson.annotations.SerializedName
import com.npv.ads.domain.natives.models.NativeDisplaySetting
import com.npv.ads.domain.natives.models.NativeType

class NativeDisplaySettingEntity(
    @SerializedName("id") private val id: String,
    @SerializedName("show") private val show: Boolean,
    @SerializedName("type") private val type: Int
) {

    companion object {
        private const val SMALL_TYPE = 0

        private const val MEDIUM_TYPE = 1
    }

    fun toNativeDisplaySetting(): NativeDisplaySetting {
        val type = when (type) {
            SMALL_TYPE -> NativeType.SMALL
            MEDIUM_TYPE -> NativeType.MEDIUM
            else -> NativeType.SMALL
        }
        return NativeDisplaySetting(id, show, type)
    }
}