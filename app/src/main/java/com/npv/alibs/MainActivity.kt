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
import com.npv.ads.admob.banners.use_case.BannerAdManager
import com.npv.ads.natives.use_case.GetNativeAdUseCase
import com.npv.ads.natives.use_case.LoadNativeAdUseCase
import com.npv.ads.natives.use_case.SetNativeConfigUseCase
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
    lateinit var setNativeConfigUseCase: SetNativeConfigUseCase

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

        val nativeJson = "{\n" +
                "  \"native_display_settings\": [\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.ONBOARDING_LANGUAGE_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.ONBOARDING_ACTIVITY\",\n" +
                "      \"show\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.DAILY_REWARD_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.BATTERY_INFORMATION_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.SETTING_LANGUAGE_ACTIVITY\",\n" +
                "      \"show\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.WIDGET_COMPLETE_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.BATTERY_SAVING_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.ULTRA_SAVING_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.WALLPAPER_COMPLETE_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.LIVE_WALLPAPER_DETAIL_ACTIVITY\",\n" +
                "      \"show\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.CHARGING_COMPLETE_FRAGMENT\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.THEME_DETAIL_FRAGMENT\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"com.wavez.battery.charging.features.ads.SET_WIDGET_ACTIVITY\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_ViewMoreChargingActivity\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_ViewMoreWallpaperActivity\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_BatteryThemesFragment\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_EarnCreditActivity\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_WidgetsFragment\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_WallpaperFragment\",\n" +
                "      \"show\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ADAPTER_NATIVE_ON_MyWallpaperFragment\",\n" +
                "      \"show\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"preload_max\": 2\n" +
                "}"
        setNativeConfigUseCase(nativeJson)

        templateView = findViewById(R.id.template_view)
        btnLoadNativeAd = findViewById(R.id.btn_load_native)
        bannerView = findViewById(R.id.banner_view)

        nativeViewModel.getNativeChangedLiveData(listOf(AdDistributor.ADMOB)).observe(this) {
            nativeViewModel.bind(AdDistributor.ADMOB, templateView, "com.wavez.battery.charging.features.ads.ONBOARDING_LANGUAGE_ACTIVITY")
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