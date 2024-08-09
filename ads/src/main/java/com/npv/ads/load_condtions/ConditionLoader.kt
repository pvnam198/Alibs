package com.npv.ads.load_condtions

interface ConditionLoader {

    fun shouldLoad(): Boolean

    fun onLoading()

    fun onLoaded()

    fun onFailed()

}