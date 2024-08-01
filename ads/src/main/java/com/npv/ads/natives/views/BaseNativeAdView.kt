package com.npv.ads.natives.views

import com.npv.ads.natives.repositories.INativeAdRepository

abstract class BaseNativeAdView<T>(
    private val repository: INativeAdRepository<T>
) : INativeAdView<T> {

    override fun bind(templateView: ITemplateView<T>, nativeDisplaySettingId: String?): Boolean {
        val nativeDisplaySetting = if (nativeDisplaySettingId == null) null
        else repository.getNativeDisplaySetting(nativeDisplaySettingId)
        val shouldDisplay = nativeDisplaySetting == null || nativeDisplaySetting.show
        if (shouldDisplay) {
            repository.getNativeAd()?.let {
                templateView.setNativeAd(it)
                return true
            }
        }
        return false
    }
}