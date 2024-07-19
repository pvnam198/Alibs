package com.npv.ads.domain.natives.models

class NativeSetting(
    val preloadMax: Int,
    private val nativeDisplaySettings: List<NativeDisplaySetting>
) {
    fun getNativeDisplaySettingsMap(): Map<String, NativeDisplaySetting> {
        val map = HashMap<String, NativeDisplaySetting>()
        nativeDisplaySettings.forEach { map[it.id] = it }
        return map
    }
}