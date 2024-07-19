package com.npv.ads.domain.natives.holders

interface INativeHolder<T> {

    fun getNativeAd(): T?

    fun add(nativeAd: T)

}