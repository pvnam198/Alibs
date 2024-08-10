package com.npv.ads.models

interface MoreCondition {

    fun shouldLoad(): Boolean

    fun shouldShow(): Boolean

}