package com.npv.ads.natives.models

import android.content.Context
import android.view.View
import com.npv.ads.AdDistributor
import com.npv.ads.di.AppModuleEntry
import com.npv.ads.natives.views.ITemplateView
import dagger.hilt.android.EntryPointAccessors

class AdapterNativeHolder(
    private val adDistributor: AdDistributor,
    private val template: ITemplateView<*>,
    private val nativeSettingDisplayId: String? = null
) {

    private var bind = false

    fun getView(context: Context): View? {
        if (bind) return template.view
        val binder = EntryPointAccessors.fromApplication(
            context, AppModuleEntry::class.java
        ).getNativeAdViewBinder()
        if (binder.bind(adDistributor, template, nativeSettingDisplayId)) {
            return template.view
        }
        return null
    }

}