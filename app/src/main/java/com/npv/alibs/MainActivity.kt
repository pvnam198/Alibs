package com.npv.alibs

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.banners.use_case.IShowBannerUseCase
import com.npv.ads.banners.use_case.SetBannerSettingsUseCase
import com.npv.ads.di.BannerModule.ShowBannerAdmobUseCase
import com.npv.ads.natives.view_models.AdMobNativeAdViewModel
import com.npv.ads.natives.view_models.INativeAdViewModel
import com.npv.alibs.nativetemplates.TemplateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @ShowBannerAdmobUseCase
    lateinit var showBannerUseCase: IShowBannerUseCase

    @Inject
    lateinit var setBannerSettingsUseCase: SetBannerSettingsUseCase

    private val nativeViewModel: INativeAdViewModel<NativeAd> by viewModels<AdMobNativeAdViewModel>()

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

        setBannerSettingsUseCase.invoke("")

        nativeViewModel.nativeChanged.observe(this) {
            nativeViewModel.bind(templateView)
        }

        btnLoadNativeAd.setOnClickListener {
            loadNative()
        }

        showBannerUseCase.showIfNeed(bannerView, "ca-app-pub-3940256099942544/9214589741")

    }

    private fun loadNative() {
        nativeViewModel.loadIfNeed("ca-app-pub-3940256099942544/2247696110")
    }
}