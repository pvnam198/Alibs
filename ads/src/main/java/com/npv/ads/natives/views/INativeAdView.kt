package com.npv.ads.natives.views

interface INativeAdView<T> {
    fun bind(templateView: ITemplateView<T>, nativeDisplaySettingId: String? = null): Boolean
}