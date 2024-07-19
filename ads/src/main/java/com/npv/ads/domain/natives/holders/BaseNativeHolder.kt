package com.npv.ads.domain.natives.holders

abstract class BaseNativeHolder<T> : INativeHolder<T> {

    private val natives = ArrayList<T>()

    override fun getNativeAd(): T? {
        try {
            val nativeAd = natives[0]
            natives.remove(nativeAd)
            return nativeAd
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun add(nativeAd: T) {
        natives.add(nativeAd)
    }

}