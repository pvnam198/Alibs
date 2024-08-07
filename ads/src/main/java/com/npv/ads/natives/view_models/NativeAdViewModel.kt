package com.npv.ads.natives.view_models

import androidx.lifecycle.LiveData
import com.npv.ads.AdDistributor
import com.npv.ads.natives.views.ITemplateView

interface NativeAdViewModel {
    fun getNativeChangedLiveData(adDistributors: List<AdDistributor>): LiveData<Boolean>

    fun loadIfNeed(adType: AdDistributor, id: String)

    fun bind(
        adDistributor: AdDistributor,
        templateView: ITemplateView<*>,
        nativeDisplaySettingId: String? = null
    )

    fun isBinded(templateView: ITemplateView<*>): Boolean

    fun setNativeSettings(json: String)
}