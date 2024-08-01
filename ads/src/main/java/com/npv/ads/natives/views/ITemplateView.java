package com.npv.ads.natives.views;

public interface ITemplateView<T> {

    void setNativeAd(T nativeAd);

    void destroyNativeAd();
}
