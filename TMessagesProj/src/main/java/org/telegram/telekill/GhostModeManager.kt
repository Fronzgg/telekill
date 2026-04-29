package org.telegram.telekill

import org.telegram.messenger.AccountInstance
import org.telegram.messenger.UserConfig
import org.telegram.tgnet.TLRPC

/**
 * GhostModeManager — перехватывает отправку статусов seen/typing/online.
 * Вызывается перед отправкой соответствующих запросов в MessagesController.
 */
object GhostModeManager {

    /**
     * Проверяет, нужно ли блокировать отправку статуса "прочитано"
     * для конкретного диалога.
     */
    fun shouldBlockReadStatus(dialogId: Long): Boolean {
        if (!TeleKillConfig.ghostModeEnabled) return false
        if (!TeleKillConfig.ghostHideReadStatus) return false
        return isDialogAffected(dialogId)
    }

    /**
     * Проверяет, нужно ли блокировать отправку статуса "печатает".
     */
    fun shouldBlockTyping(dialogId: Long): Boolean {
        if (!TeleKillConfig.ghostModeEnabled) return false
        if (!TeleKillConfig.ghostHideTyping) return false
        return isDialogAffected(dialogId)
    }

    /**
     * Проверяет, нужно ли блокировать обновление статуса online.
     */
    fun shouldBlockOnlineStatus(): Boolean {
        if (!TeleKillConfig.ghostModeEnabled) return false
        return TeleKillConfig.ghostHideOnline
    }

    /**
     * Определяет, попадает ли диалог под текущий режим призрака.
     */
    private fun isDialogAffected(dialogId: Long): Boolean {
        return when (TeleKillConfig.ghostModeType) {
            TeleKillConfig.GHOST_TYPE_ALL -> true
            TeleKillConfig.GHOST_TYPE_CONTACTS_ONLY -> isContact(dialogId)
            TeleKillConfig.GHOST_TYPE_EXCEPT_FAVORITES -> !isFavorite(dialogId)
            TeleKillConfig.GHOST_TYPE_GROUPS_ONLY -> isGroup(dialogId)
            else -> true
        }
    }

    private fun isContact(dialogId: Long): Boolean {
        val currentAccount = UserConfig.selectedAccount
        val user = AccountInstance.getInstance(currentAccount)
            .messagesController.getUser(dialogId) ?: return false
        return user.contact
    }

    private fun isFavorite(dialogId: Long): Boolean {
        // Избранные — диалоги помеченные звёздочкой (pinned + folder logic)
        // Упрощённая реализация: считаем закреплённые чаты избранными
        val currentAccount = UserConfig.selectedAccount
        val dialog = AccountInstance.getInstance(currentAccount)
            .messagesController.dialogs_dict.get(dialogId) ?: return false
        return dialog.pinned
    }

    private fun isGroup(dialogId: Long): Boolean {
        // Группы имеют отрицательный dialogId
        return dialogId < 0
    }
}
