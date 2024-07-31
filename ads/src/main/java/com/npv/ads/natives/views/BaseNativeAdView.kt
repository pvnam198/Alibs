package com.npv.ads.natives.views

import com.npv.ads.natives.repositories.INativeAdRepository

abstract class BaseNativeAdView<T>(
    private val repository: INativeAdRepository<T>
) : INativeAdView<T> {

    private var isBind = false

    override fun bind(nativeDisplaySettingId: String?): Boolean {
        val nativeDisplaySetting = if (nativeDisplaySettingId == null) null
        else repository.getNativeDisplaySetting(nativeDisplaySettingId)
        val shouldDisplay = nativeDisplaySetting == null || nativeDisplaySetting.show
        if (!shouldDisplay) {
            return false
        }
        if (isBind) return true
        repository.getNativeAd()?.let {
            isBind = true
            onBind(it)
        }
        return isBind
    }

    abstract fun onBind(nativeAd: T)

}