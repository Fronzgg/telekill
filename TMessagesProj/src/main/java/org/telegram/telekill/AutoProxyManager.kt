package org.telegram.telekill

import org.telegram.messenger.AccountInstance
import org.telegram.messenger.SharedConfig
import org.telegram.messenger.UserConfig

/**
 * AutoProxyManager — при первом запуске автоматически добавляет
 * предустановленный MTProto прокси. Пользователь видит его в настройках
 * и может управлять им.
 */
object AutoProxyManager {

    // Предустановленный прокси
    private const val PROXY_SERVER = "ee07.tcpdoor.net"
    private const val PROXY_PORT = 443
    private const val PROXY_SECRET = "ee1ad6b4d34f94e700dcee2e86ad8862747777772e31632e7275"

    /**
     * Вызывается при старте приложения (из ApplicationLoader или LaunchActivity).
     * Добавляет прокси только один раз при первом запуске.
     */
    fun setupIfNeeded() {
        if (TeleKillConfig.proxySetupDone) return
        if (!TeleKillConfig.autoProxyEnabled) return

        addProxy()
        TeleKillConfig.proxySetupDone = true
    }

    private fun addProxy() {
        val proxyInfo = SharedConfig.ProxyInfo(
            PROXY_SERVER,
            PROXY_PORT,
            "", // username — не нужен для MTProto
            "", // password — не нужен для MTProto
            PROXY_SECRET
        )

        // Добавляем в список и делаем активным
        SharedConfig.addProxy(proxyInfo)
        SharedConfig.setCurrentProxy(proxyInfo)
        SharedConfig.saveProxyList()

        // Применяем для всех аккаунтов
        for (i in 0 until UserConfig.MAX_ACCOUNT_COUNT) {
            if (UserConfig.getInstance(i).isClientActivated) {
                AccountInstance.getInstance(i).connectionManager.setProxySettings(
                    true,
                    PROXY_SERVER,
                    PROXY_PORT,
                    "",
                    "",
                    PROXY_SECRET
                )
            }
        }
    }

    /**
     * Переподключение к прокси при смене сети.
     * Вызывается из NetworkChangeReceiver.
     */
    fun reconnectOnNetworkChange() {
        if (!TeleKillConfig.autoProxyEnabled) return
        if (!TeleKillConfig.proxySetupDone) return

        for (i in 0 until UserConfig.MAX_ACCOUNT_COUNT) {
            if (UserConfig.getInstance(i).isClientActivated) {
                AccountInstance.getInstance(i).connectionManager.checkConnection()
            }
        }
    }
}
