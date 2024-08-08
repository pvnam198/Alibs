package com.npv.ads.admob.natives.view_models

import androidx.lifecycle.LiveData
import com.npv.ads.AdDistributor
import com.npv.ads.admob.natives.views.TemplateView

interface NativeAdViewModel {
    fun getNativeChangedLiveData(adDistributors: List<AdDistributor>): LiveData<Boolean>

    fun loadIfNeed(adType: AdDistributor, id: String)

    fun bind(
        adDistributor: AdDistributor,
        templateView: TemplateView<*>,
        nativeDisplaySettingId: String? = null
    )

    fun isBinded(templateView: TemplateView<*>): Boolean

    fun setNativeSettings(json: String)
}