package com.npv.ads.app

import android.content.Context
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseAdsManager(private val context: Context) : AdsManager {
    override suspend fun init(){
        withContext(Dispatchers.IO){
            MobileAds.initialize(context) {}
        }
    }
}