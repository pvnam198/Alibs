package com.npv.alibs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.nativead.NativeAd
import com.npv.ads.admob.banners.manager.AdmobBannerManager
import com.npv.ads.admob.interstitial.manager.InterstitialAdManager
import com.npv.ads.models.banners.BannerSize
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
    lateinit var interstitialAdManager: InterstitialAdManager

    private lateinit var bannerView: FrameLayout
    private lateinit var templateView: TemplateView
    private lateinit var btnLoadNativeAd: View
    private lateinit var btnLoadInterstitial: View
    private lateinit var btnShowInterstitial: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        templateView = findViewById(R.id.template_view)
        btnLoadNativeAd = findViewById(R.id.btn_load_native)
        bannerView = findViewById(R.id.banner_view)
        btnLoadInterstitial = findViewById(R.id.btn_load_interstitial)
        btnShowInterstitial = findViewById(R.id.btn_show_interstitial)

        // Get nativeAd
        val nativeAd: NativeAd? = nativeAdRepository.getNativeAd()

        // Get specialNativeAd with id "main_screen"
        // If there is a specialNativeAd marked as main_screen return it else...
        // ...if not exists, load new native, otherwise return available native and mark as main_screen
        var specialNativeAd: NativeAd? = nativeAdRepository.getNativeAd("main_screen")

        // Release native with id "main_screen"
        // nativeAdRepository.releaseNative("main_screen")

        val nativeAdChangedListener = object : NativeAdChangedListener {
            override fun onNativeChanged() {
                specialNativeAd = nativeAdRepository.getNativeAd("main_screen")
                Log.d("log_debugs", "MainActivity_onNativeChanged: $specialNativeAd")
                specialNativeAd?.let {
                    templateView.setNativeAd(it)
                }
            }
        }

        // Listen native ad change
        nativeAdRepository.addListener(nativeAdChangedListener)

        // Remove listener
        // nativeAdRepository.removeListener(nativeAdChangedListener)

        // Set NativeAdSetting
        nativeAdRepository.setNativeAdSetting("json")

        // Load native
        nativeAdRepository.load("ca-app-pub-3940256099942544/2247696110")

        // Load banner
        admobBannerManager.load(
            "ca-app-pub-3940256099942544/2014213617",
            bannerSize = null,
            type = null,
            callback = { adView ->
                if (adView != null) {
                    bannerView.visibility = View.VISIBLE
                    bannerView.removeAllViews()
                    bannerView.addView(adView)
                } else {
                    bannerView.visibility = View.GONE
                }
            })

        // bannerView with show if loaded else hide
        admobBannerManager.displayAdIfLoaded(
            adUnitId = "ca-app-pub-3940256099942544/2014213617",
            parent = bannerView,
            bannerSize = BannerSize.FitParent(bannerView)
        )

        // Load interstitial ad
        interstitialAdManager.load("ca-app-pub-3940256099942544/1033173712")

        // Show interstitial ad
        btnShowInterstitial.setOnClickListener {

            // If preloadAdUnitId != null, load new interstitial ad
            interstitialAdManager.show(this, onDismiss = {

            }, preloadAdUnitId = "ca-app-pub-3940256099942544/1033173712")
        }
    }
}