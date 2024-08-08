package com.npv.ads.admob.natives.views

import com.npv.ads.AdDistributor
import com.npv.ads.natives.use_case.GetNativeAdUseCase
import com.npv.ads.natives.use_case.ShouldShowNativeAdUseCase

class NativeAdViewImpl(
    private val shouldShowNativeAdUseCase: ShouldShowNativeAdUseCase,
    private val getNativeAdUseCase: GetNativeAdUseCase,
) : NativeAdViewBinder {

    override fun <Ad> bind(
        adDistributor: AdDistributor,
        templateView: TemplateView<Ad>,
        nativeDisplaySettingId: String?
    ): Boolean {
        if (shouldShowNativeAdUseCase(adDistributor, nativeDisplaySettingId)) {
            getNativeAdUseCase<Ad>(adDistributor)?.let {
                templateView.setNativeAd(it)
                return true
            }
        }
        return false
    }
}