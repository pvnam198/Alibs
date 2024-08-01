package com.npv.ads.natives.view_models

import androidx.lifecycle.LiveData
import com.npv.ads.natives.views.ITemplateView

interface INativeAdViewModel<T> {
    val nativeChanged: LiveData<Boolean>

    fun loadIfNeed(id: String)

    fun bind(templateView: ITemplateView<T>, nativeDisplaySettingId: String? = null)
}