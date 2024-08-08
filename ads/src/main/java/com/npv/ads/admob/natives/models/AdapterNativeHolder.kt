package com.npv.ads.admob.natives.models

import android.content.Context
import android.view.View
import com.npv.ads.AdDistributor
import com.npv.ads.admob.natives.views.TemplateView
import dagger.hilt.android.EntryPointAccessors

class AdapterNativeHolder(
    private val adDistributor: AdDistributor,
    private val template: TemplateView<*>,
    private val nativeSettingDisplayId: String? = null
) {

    private var bind = false

    fun getView(context: Context): View? {
        if (bind) return template.view
        val binder = EntryPointAccessors.fromApplication(
            context, AppModuleEntry::class.java
        ).getNativeAdViewBinder()
        if (binder.bind(adDistributor, template, nativeSettingDisplayId)) {
            bind = true
            return template.view
        }
        return null
    }

}