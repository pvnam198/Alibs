package com.npv.ads.admob.natives.views;

import android.view.View;

import com.google.android.gms.ads.nativead.NativeAd;

public interface TemplateView {

    void setNativeAd(NativeAd nativeAd);

    View getView();

}
