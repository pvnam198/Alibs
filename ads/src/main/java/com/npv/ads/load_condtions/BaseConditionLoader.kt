package com.npv.ads.load_condtions

import com.npv.ads.Constant.MAX_FAILED_LOAD_TIME
import com.npv.ads.Constant.MIN_FAILED_LOAD_TIME

abstract class BaseConditionLoader : ConditionLoader {

    private var isLoading = false
    private var numberFailedLoad = 0
    private var timeLastFailedLoad = 0L

    override fun shouldLoad(): Boolean {
        return !(isLoading || isTimeout())
    }

    override fun onLoading() {
        isLoading = true
    }

    override fun onLoaded() {
        isLoading = false
        numberFailedLoad = 0
        timeLastFailedLoad = 0
    }

    override fun onFailed() {
        isLoading = false
        timeLastFailedLoad = System.currentTimeMillis()
        numberFailedLoad++
    }
    
    private fun isTimeout(): Boolean {
        val lastFailedTimeToCurrentTime = System.currentTimeMillis() - timeLastFailedLoad
        var pendingTime = MIN_FAILED_LOAD_TIME * numberFailedLoad
        if (pendingTime > MAX_FAILED_LOAD_TIME) {
            pendingTime = MAX_FAILED_LOAD_TIME
        }
        return lastFailedTimeToCurrentTime < pendingTime
    }
}