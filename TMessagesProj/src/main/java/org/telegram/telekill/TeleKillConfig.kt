package org.telegram.telekill

import android.content.Context
import android.content.SharedPreferences

object TeleKillConfig {

    private const val PREFS_NAME = "telekill_config"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Ghost Mode
    var ghostModeEnabled: Boolean
        get() = prefs.getBoolean("ghost_mode", false)
        set(value) = prefs.edit().putBoolean("ghost_mode", value).apply()

    var ghostHideReadStatus: Boolean
        get() = prefs.getBoolean("ghost_hide_read", true)
        set(value) = prefs.edit().putBoolean("ghost_hide_read", value).apply()

    var ghostHideTyping: Boolean
        get() = prefs.getBoolean("ghost_hide_typing", true)
        set(value) = prefs.edit().putBoolean("ghost_hide_typing", value).apply()

    var ghostHideOnline: Boolean
        get() = prefs.getBoolean("ghost_hide_online", true)
        set(value) = prefs.edit().putBoolean("ghost_hide_online", value).apply()

    // Ghost mode types
    var ghostModeType: Int
        get() = prefs.getInt("ghost_mode_type", GHOST_TYPE_ALL)
        set(value) = prefs.edit().putInt("ghost_mode_type", value).apply()

    // Proxy
    var autoProxyEnabled: Boolean
        get() = prefs.getBoolean("auto_proxy", true)
        set(value) = prefs.edit().putBoolean("auto_proxy", value).apply()

    var proxySetupDone: Boolean
        get() = prefs.getBoolean("proxy_setup_done", false)
        set(value) = prefs.edit().putBoolean("proxy_setup_done", value).apply()

    companion object {
        const val GHOST_TYPE_ALL = 0           // для всех
        const val GHOST_TYPE_CONTACTS_ONLY = 1 // только для контактов
        const val GHOST_TYPE_EXCEPT_FAVORITES = 2 // для всех кроме избранных
        const val GHOST_TYPE_GROUPS_ONLY = 3   // только в группах
    }
}
