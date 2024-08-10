package com.npv.ads.admob.interstitial.repositories

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.npv.ads.admob.revenue_tracker.InterstitialRevenueTracker
import com.npv.ads.section_loader.SectionLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sectionLoader: SectionLoader,
    private val revenueTracker: InterstitialRevenueTracker
) : InterstitialRepository {

    private val lockLoad = Any()
    override var interstitialAd: InterstitialAd? = null

    override fun load(adUnitId: String) {
        synchronized(lockLoad) {
            if (interstitialAd != null) return
            if (!sectionLoader.shouldLoad()) return

            sectionLoader.onLoading()
            InterstitialAd.load(context,
                adUnitId,
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        sectionLoader.onFailed()
                    }

                    override fun onAdLoaded(ad: InterstitialAd) {
                        super.onAdLoaded(ad)
                        sectionLoader.onLoaded()
                        interstitialAd = ad
                        revenueTracker.trackAdRevenue(ad)
                    }
                })
        }
    }

}