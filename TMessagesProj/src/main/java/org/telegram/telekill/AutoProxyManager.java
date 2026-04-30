package org.telegram.telekill;

import android.content.Context;
import android.content.SharedPreferences;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.SharedConfig;
import org.telegram.tgnet.ConnectionsManager;

public class AutoProxyManager {

    private static final String PROXY_SERVER = "ee07.tcpdoor.net";
    private static final int PROXY_PORT = 443;
    private static final String PROXY_SECRET = "ee1ad6b4d34f94e700dcee2e86ad8862747777772e31632e7275";

    /**
     * Вызывается при старте приложения.
     * Добавляет MTProto прокси при первом запуске — пользователь видит его в настройках.
     */
    public static void setupIfNeeded(Context context) {
        if (TeleKillConfig.isProxySetupDone()) return;
        if (!TeleKillConfig.isAutoProxyEnabled()) return;

        try {
            addProxy();
            TeleKillConfig.setProxySetupDone(true);
        } catch (Exception e) {
            // не ломаем запуск если что-то пошло не так
        }
    }

    private static void addProxy() {
        SharedConfig.ProxyInfo proxyInfo = new SharedConfig.ProxyInfo(
                PROXY_SERVER,
                PROXY_PORT,
                "",
                "",
                PROXY_SECRET
        );

        // addProxy добавляет в список и сохраняет, возвращает существующий если уже есть
        SharedConfig.ProxyInfo added = SharedConfig.addProxy(proxyInfo);

        // делаем активным
        SharedConfig.currentProxy = added;

        // сохраняем как текущий в настройках
        SharedPreferences.Editor editor = org.telegram.messenger.MessagesController.getGlobalMainSettings().edit();
        editor.putString("proxy_ip", PROXY_SERVER);
        editor.putInt("proxy_port", PROXY_PORT);
        editor.putString("proxy_user", "");
        editor.putString("proxy_pass", "");
        editor.putString("proxy_secret", PROXY_SECRET);
        editor.putBoolean("proxy_enabled", true);
        editor.apply();

        // применяем для всех аккаунтов
        ConnectionsManager.setProxySettings(
                true,
                PROXY_SERVER,
                PROXY_PORT,
                "",
                "",
                PROXY_SECRET
        );
    }
}
