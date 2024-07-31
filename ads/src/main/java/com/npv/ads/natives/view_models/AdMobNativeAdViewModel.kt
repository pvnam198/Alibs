package com.npv.ads.natives.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.repositories.INativeAdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdMobNativeAdViewModel @Inject constructor(
    private val nativeAdRepository: INativeAdRepository<NativeAd>
) : ViewModel() {

    private val _nativeChanged = MutableLiveData<Boolean>()
    val nativeChanged: LiveData<Boolean> = _nativeChanged

    private val nativeAdChangedListener = object : NativeAdChangedListener {
        override fun onNativeChanged() {
            _nativeChanged.value = true
        }
    }

    init {
        nativeAdRepository.addListener(nativeAdChangedListener)
    }

    fun loadIfNeed(id: String) {
        nativeAdRepository.loadIfNeed(id)
    }

    override fun onCleared() {
        nativeAdRepository.removeListener(nativeAdChangedListener)
        super.onCleared()
    }

}