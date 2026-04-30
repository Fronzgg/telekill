package org.telegram.telekill;

import android.content.Context;
import android.content.SharedPreferences;

public class TeleKillConfig {

    private static final String PREFS_NAME = "telekill_config";
    private static SharedPreferences prefs;

    public static final int GHOST_TYPE_ALL = 0;
    public static final int GHOST_TYPE_CONTACTS_ONLY = 1;
    public static final int GHOST_TYPE_EXCEPT_FAVORITES = 2;
    public static final int GHOST_TYPE_GROUPS_ONLY = 3;

    public static void init(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Ghost Mode
    public static boolean isGhostModeEnabled() {
        return prefs.getBoolean("ghost_mode", false);
    }
    public static void setGhostModeEnabled(boolean value) {
        prefs.edit().putBoolean("ghost_mode", value).apply();
    }

    public static boolean isGhostHideReadStatus() {
        return prefs.getBoolean("ghost_hide_read", true);
    }
    public static void setGhostHideReadStatus(boolean value) {
        prefs.edit().putBoolean("ghost_hide_read", value).apply();
    }

    public static boolean isGhostHideTyping() {
        return prefs.getBoolean("ghost_hide_typing", true);
    }
    public static void setGhostHideTyping(boolean value) {
        prefs.edit().putBoolean("ghost_hide_typing", value).apply();
    }

    public static boolean isGhostHideOnline() {
        return prefs.getBoolean("ghost_hide_online", true);
    }
    public static void setGhostHideOnline(boolean value) {
        prefs.edit().putBoolean("ghost_hide_online", value).apply();
    }

    public static int getGhostModeType() {
        return prefs.getInt("ghost_mode_type", GHOST_TYPE_ALL);
    }
    public static void setGhostModeType(int value) {
        prefs.edit().putInt("ghost_mode_type", value).apply();
    }

    // Proxy
    public static boolean isAutoProxyEnabled() {
        return prefs.getBoolean("auto_proxy", true);
    }
    public static void setAutoProxyEnabled(boolean value) {
        prefs.edit().putBoolean("auto_proxy", value).apply();
    }

    public static boolean isProxySetupDone() {
        return prefs.getBoolean("proxy_setup_done", false);
    }
    public static void setProxySetupDone(boolean value) {
        prefs.edit().putBoolean("proxy_setup_done", value).apply();
    }
}
