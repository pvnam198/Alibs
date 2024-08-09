package com.npv.ads.load_condtions

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ConditionLoaderAppModule.NativeConditionLoader
class NativeConditionLoaderImpl @Inject constructor() : BaseConditionLoader()