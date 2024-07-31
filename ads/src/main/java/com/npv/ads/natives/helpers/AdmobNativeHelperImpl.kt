package com.npv.ads.natives.helpers

import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.repositories.INativeAdRepository

class AdmobNativeHelperImpl(
    private val nativeAdRepository: INativeAdRepository<NativeAd>
) : INativeAdHelper<NativeAd> {

    override fun addListener(ls: NativeAdChangedListener) {
        nativeAdRepository.addListener(ls)
    }

    override fun removeListener(ls: NativeAdChangedListener) {
        nativeAdRepository.removeListener(ls)
    }

    override fun getNativeAd(): NativeAd? {
        return nativeAdRepository.getNativeAd()
    }

    override fun shouldShow(nativeDisplaySettingId: String): Boolean {
        val nativeDisplaySetting =
            nativeAdRepository.getNativeDisplaySetting(nativeDisplaySettingId)
        return nativeDisplaySetting == null || nativeDisplaySetting.show
    }

    override fun setNativeSettings(json: String) {
        nativeAdRepository.setNativeSettings(json)
    }

    override fun loadIfNeed(id: String) {
        nativeAdRepository.loadIfNeed(id)
    }
}