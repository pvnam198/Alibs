package com.npv.ads.natives.views;

import android.view.View;

public interface ITemplateView<T> {

    void setNativeAd(T nativeAd);

    View getView();

}
