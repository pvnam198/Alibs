package com.npv.ads.natives.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.repositories.INativeAdRepository
import com.npv.ads.natives.views.INativeAdView
import com.npv.ads.natives.views.ITemplateView

abstract class BaseNativeAdViewModel<Ad>(
    private val repo: INativeAdRepository<Ad>,
    private val nativeAdView: INativeAdView<Ad>
) : ViewModel(), INativeAdViewModel<Ad> {

    private val _nativeChanged = MutableLiveData<Boolean>()
    override val nativeChanged: LiveData<Boolean> = _nativeChanged

    private val nativeViewsMap = HashMap<ITemplateView<Ad>, Boolean>()

    private val nativeAdChangedListener = object : NativeAdChangedListener {
        override fun onNativeChanged() {
            _nativeChanged.value = true
        }
    }

    init {
        repo.addListener(nativeAdChangedListener)
    }

    override fun loadIfNeed(id: String) {
        repo.loadIfNeed(id)
    }

    override fun onCleared() {
        repo.removeListener(nativeAdChangedListener)
        super.onCleared()
    }

    override fun bind(templateView: ITemplateView<Ad>, nativeDisplaySettingId: String?) {
        val isBind = nativeViewsMap[templateView] ?: false
        if (isBind) return
        nativeViewsMap[templateView] = nativeAdView.bind(templateView, nativeDisplaySettingId)
    }

}