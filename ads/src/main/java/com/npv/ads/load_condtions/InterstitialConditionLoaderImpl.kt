package com.npv.ads.load_condtions

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ConditionLoaderAppModule.InterstitialConditionLoader
class InterstitialConditionLoaderImpl @Inject constructor() : BaseConditionLoader()