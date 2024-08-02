package com.npv.alibs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.AdDistributor
import com.npv.ads.banners.use_case.BannerAdManager
import com.npv.ads.natives.use_case.GetNativeAdUseCase
import com.npv.ads.natives.use_case.LoadNativeAdUseCase
import com.npv.ads.natives.view_models.NativeAdViewModel
import com.npv.ads.natives.view_models.NativeAdViewModelImpl
import com.npv.ads.natives.views.NativeAdViewBinder
import com.npv.alibs.nativetemplates.TemplateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bannerAdManager: BannerAdManager

    @Inject
    lateinit var getNativeAdUseCase: GetNativeAdUseCase

    @Inject
    lateinit var loadNativeAdUseCase: LoadNativeAdUseCase

    @Inject
    lateinit var nativeAdView: NativeAdViewBinder

    private val nativeViewModel: NativeAdViewModel by viewModels<NativeAdViewModelImpl>()

    private lateinit var bannerView: FrameLayout
    private lateinit var templateView: TemplateView
    private lateinit var btnLoadNativeAd: View

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
        bannerView = findViewById(R.id.banner_view)

        nativeViewModel.getNativeChangedLiveData(listOf(AdDistributor.ADMOB)).observe(this) {
            nativeViewModel.bind(AdDistributor.ADMOB, templateView)
        }

        btnLoadNativeAd.setOnClickListener {
            loadNative()
        }

        bannerAdManager.showIfNeed(
            AdDistributor.ADMOB,
            bannerView,
            "ca-app-pub-3940256099942544/6300978111",
        )

        Log.d(
            "log_debugs",
            "MainActivity_onCreate: ${getNativeAdUseCase.invoke<NativeAd>(AdDistributor.ADMOB)}"
        )

    }

    private fun loadNative() {
        loadNativeAdUseCase.load(AdDistributor.ADMOB, "ca-app-pub-3940256099942544/2247696110")
    }
}