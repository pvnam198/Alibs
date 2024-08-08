package com.npv.ads.admob.natives.view_models

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.npv.ads.AdDistributor
import com.npv.ads.natives.repositories.NativeAdRepository
import com.npv.ads.natives.use_case.GetNativeAdRepositoryUseCase
import com.npv.ads.natives.use_case.LoadNativeAdUseCase
import com.npv.ads.natives.use_case.SetNativeConfigUseCase
import com.npv.ads.admob.natives.views.TemplateView
import com.npv.ads.natives.views.NativeAdViewBinder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NativeAdViewModelImpl @Inject constructor(
    private val loadNativeAdUseCase: LoadNativeAdUseCase,
    private val getNativeAdRepositoryUseCase: GetNativeAdRepositoryUseCase,
    private val nativeAdViewBinder: NativeAdViewBinder,
    private val setNativeConfigUseCase: SetNativeConfigUseCase,
) : ViewModel(), NativeAdViewModel {

    private val _nativeChanged = MutableLiveData<Boolean>()

    private val nativeViewsMap = HashMap<TemplateView<*>, Boolean>()

    private var adDistributors: List<AdDistributor> = emptyList()

    private val admobNativeAdChangedListener = object :
        com.npv.ads.admob.natives.listeners.NativeAdChangedListener {
        override fun onNativeChanged() {
            if (adDistributors.find { it == AdDistributor.ADMOB } != null) {
                _nativeChanged.value = true
            }
        }
    }

    private val admobNativeAdRepository: NativeAdRepository? by lazy {
        getNativeAdRepositoryUseCase(
            AdDistributor.ADMOB
        )
    }

    init {
        admobNativeAdRepository?.addListener(admobNativeAdChangedListener)
    }

    override fun onCleared() {
        admobNativeAdRepository?.removeListener(admobNativeAdChangedListener)
        super.onCleared()
    }

    override fun getNativeChangedLiveData(adDistributors: List<AdDistributor>): LiveData<Boolean> {
        this.adDistributors = adDistributors
        return _nativeChanged
    }

    override fun loadIfNeed(adType: AdDistributor, id: String) {
        loadNativeAdUseCase.load(adType, id)
    }

    override fun bind(
        adDistributor: AdDistributor,
        templateView: TemplateView<*>,
        nativeDisplaySettingId: String?
    ) {
        val isBind = nativeViewsMap[templateView] ?: false
        if (isBind) return
        nativeViewsMap[templateView] =
            nativeAdViewBinder.bind(adDistributor, templateView, nativeDisplaySettingId)
        if (nativeViewsMap[templateView] == true) {
            templateView.view?.visibility = View.VISIBLE
        } else {
            templateView.view?.visibility = View.GONE
        }
    }

    override fun isBinded(templateView: TemplateView<*>): Boolean {
        return nativeViewsMap[templateView] == true
    }

    override fun setNativeSettings(json: String) {
        setNativeConfigUseCase(json)
    }
}