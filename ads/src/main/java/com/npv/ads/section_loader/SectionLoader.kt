package com.npv.ads.section_loader

fun createSectionLoader(): SectionLoader {
    return SectionLoaderImpl()
}

interface SectionLoader {

    fun shouldLoad(): Boolean

    fun onLoading()

    fun onLoaded()

    fun onFailed()

}