package com.npv.ads.admob.banners.size_calculator

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.google.android.gms.ads.AdSize
import com.npv.ads.models.banners.BannerSize
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdSizeCalculatorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AdSizeCalculator {
    override fun calculateBannerSize(bannerSize: BannerSize?): AdSize {
        return when (bannerSize) {
            is BannerSize.FitParent -> {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = windowManager.defaultDisplay
                val outMetrics = DisplayMetrics()
                display.getMetrics(outMetrics)
                val density = outMetrics.density
                var adWidthPixels = bannerSize.parent.width.toFloat()
                if (adWidthPixels == 0f) {
                    adWidthPixels = outMetrics.widthPixels.toFloat()
                }
                val adWidth = (adWidthPixels / density).toInt()
                return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
            }

            is BannerSize.Custom -> AdSize(bannerSize.width, bannerSize.height)
            else -> AdSize.BANNER
        }
    }
}