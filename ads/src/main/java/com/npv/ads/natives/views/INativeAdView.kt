package com.npv.ads.natives.views

interface INativeAdView<T> {
    fun bind(nativeDisplaySettingId: String? = null): Boolean
}