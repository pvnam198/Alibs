package com.npv.alibs

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.natives.conditions.INativeAdConditions
import com.npv.ads.natives.helpers.AdmobNativeHelperImpl
import com.npv.ads.natives.helpers.INativeAdHelper
import com.npv.ads.natives.listeners.NativeAdChangedListener
import com.npv.ads.natives.repositories.AdmobNativeAdRepository
import com.npv.ads.natives.repositories.INativeAdRepository
import com.npv.ads.sharedPref.AdsSharedPrefImpl
import com.npv.alibs.nativetemplates.TemplateView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var templateView: TemplateView
    private lateinit var btnLoadNativeAd: View
    private var isBind = false
    private val nativeAdRepository: INativeAdRepository<NativeAd> by lazy {
        AdmobNativeAdRepository(
            context = this,
            nativeAdConditions = object : INativeAdConditions {
                override fun shouldLoad(): Boolean {
                    return true
                }

            },
            adsSharedPref = AdsSharedPrefImpl(this)
        )
    }

    private val nativeAdChangedListener = object : NativeAdChangedListener {
        override fun onNativeChanged() {
            lifecycleScope.launch {
                if (isBind) return@launch
                val nativeAd = nativeAdRepository.getNativeAd()
                if (nativeAd != null) {
                    isBind = true
                    templateView.visibility = View.VISIBLE
                    templateView.setNativeAd(nativeAd)
                    loadNativeAd()
                } else {
                    templateView.visibility = View.GONE
                }
            }
        }
    }

    private val nativeHelper: INativeAdHelper<NativeAd> by lazy {
        val nativeHelper = AdmobNativeHelperImpl(nativeAdRepository = nativeAdRepository)
        nativeHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        templateView = findViewById(R.id.template_view)
        btnLoadNativeAd = findViewById(R.id.btn_load_native)

        btnLoadNativeAd.setOnClickListener {
            loadNativeAd()
        }
    }

    private fun loadNativeAd() {
        nativeAdRepository.loadIfNeed("ca-app-pub-3940256099942544/2247696110")
    }

    override fun onResume() {
        super.onResume()
        nativeHelper.addListener(nativeAdChangedListener)
    }

    override fun onPause() {
        nativeHelper.removeListener(nativeAdChangedListener)
        super.onPause()
    }

}