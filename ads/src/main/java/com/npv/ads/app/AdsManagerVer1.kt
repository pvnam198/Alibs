package com.npv.ads.app

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsManagerVer1 @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseAdsManager(context)