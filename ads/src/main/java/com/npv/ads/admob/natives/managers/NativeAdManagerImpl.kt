package com.npv.ads.admob.natives.managers

import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.admob.natives.repositories.NativeAdRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeAdManagerImpl @Inject constructor(
    private val nativeAdRepository: NativeAdRepository
) : NativeAdManager {
    override fun load(id: String) {
        nativeAdRepository.load(id)
    }

    override fun getNativeAd(): NativeAd? {
        return nativeAdRepository.getNativeAd()
    }

    override fun getNativeAd(id: String): NativeAd? {
        TODO("Not yet implemented")
    }

}