package com.npv.alibs

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.npv.ads.data.natives.repositories.AdMobNativeAdRepository
import com.npv.ads.data.shared.AdsSharedPrefImpl
import com.npv.ads.domain.natives.conditions.INativeAdConditions
import com.npv.ads.domain.natives.listeners.NativeAdLoadingCompletedListener
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var nativeAdLoadingCompletedListener: NativeAdLoadingCompletedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadNatives()
    }

    private fun loadNatives() {
        val nativeAdRepository = AdMobNativeAdRepository(
            context = this,
            nativeAdConditions = object : INativeAdConditions {
                override fun shouldLoad(): Boolean {
                    return true
                }

            },
            adsSharedPref = AdsSharedPrefImpl(this)
        )
        lifecycleScope.launch {
            nativeAdRepository.loadIfNeed("ca-app-pub-3940256099942544/2247696110")
            val nativeAdLoadingCompletedListener = object : NativeAdLoadingCompletedListener {
                override fun onNativeAdLoadingCompleted() {
                    lifecycleScope.launch {
                        val nativeAd = nativeAdRepository.getNativeAd()
                        Log.d("log_debugs", "MainActivity_onNativeAdLoadingCompleted: $nativeAd")
                    }
                }
            }
            this@MainActivity.nativeAdLoadingCompletedListener = nativeAdLoadingCompletedListener
            nativeAdRepository.addNativeAdLoadingCompletedListener(nativeAdLoadingCompletedListener)
        }
    }
}