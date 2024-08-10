package com.npv.ads.admob.open_ad.repositories

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.npv.ads.TAG
import com.npv.ads.admob.revenue_tracker.AppOpenAdRevenueTracker
import com.npv.ads.section_loader.SectionLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdOpenRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionLoader: SectionLoader,
    private val revenueTracker: AppOpenAdRevenueTracker
) : AdOpenRepository {

    private val lockLoad = Any()

    override var appOpenAd: AppOpenAd? = null

    override fun load(adUnitId: String) {
        synchronized(lockLoad) {
            if (appOpenAd != null) return
            if (!sessionLoader.shouldLoad()) return
            sessionLoader.onLoading()
            val request = AdRequest.Builder().build()
            AppOpenAd.load(context, adUnitId, request,
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        revenueTracker.trackAdRevenue(ad)
                        appOpenAd = ad
                        sessionLoader.onLoaded()
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.d(TAG, "onAdFailedToLoad: ${loadAdError.message}")
                        sessionLoader.onFailed()
                    }
                }
            )
        }
    }
}