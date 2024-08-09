package com.npv.alibs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.npv.ads.admob.banners.manager.AdmobBannerManager
import com.npv.ads.admob.banners.models.BannerSize
import com.npv.ads.admob.interstitial.repositories.InterstitialRepository
import com.npv.ads.admob.natives.listeners.NativeAdChangedListener
import com.npv.ads.admob.natives.repositories.NativeAdRepository
import com.npv.alibs.nativetemplates.TemplateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var admobBannerManager: AdmobBannerManager

    @Inject
    lateinit var nativeAdRepository: NativeAdRepository

    @Inject
    lateinit var interstitialRepository: InterstitialRepository

    private lateinit var bannerView: FrameLayout
    private lateinit var templateView: TemplateView
    private lateinit var btnLoadNativeAd: View
    private lateinit var btnLoadInterstitial: View
    private lateinit var btnShowInterstitial: View

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


        templateView = findViewById(R.id.template_view)
        btnLoadNativeAd = findViewById(R.id.btn_load_native)
        bannerView = findViewById(R.id.banner_view)
        btnLoadInterstitial = findViewById(R.id.btn_load_interstitial)
        btnShowInterstitial = findViewById(R.id.btn_show_interstitial)

        nativeAdRepository.addListener(object : NativeAdChangedListener {
            override fun onNativeChanged() {
                val nativeAd = nativeAdRepository.getNativeAd("main_screen")
                Log.d("log_debugs", "MainActivity_onNativeChanged: $nativeAd")
                nativeAd?.let {
                    templateView.setNativeAd(it)
                    nativeAdRepository.releaseNative("main_screen")
                }
            }
        })

        nativeAdRepository.setNativeAdSetting(nativeJson)
        loadNativeAd()

        btnLoadNativeAd.setOnClickListener {
            nativeAdRepository.load("ca-app-pub-3940256099942544/2247696110")
        }

        admobBannerManager.displayAdIfLoaded(
            adUnitId = "ca-app-pub-3940256099942544/2014213617",
            parent = bannerView,
            bannerSize = BannerSize.FitParent(bannerView)
        )

        btnLoadInterstitial.setOnClickListener {
            interstitialRepository.load("ca-app-pub-3940256099942544/1033173712")
        }

        btnShowInterstitial.setOnClickListener {
            interstitialRepository.show(this)
        }

    }

    private fun loadNativeAd() {
        nativeAdRepository.load("ca-app-pub-3940256099942544/2247696110")
    }

}