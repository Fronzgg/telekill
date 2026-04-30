package org.telegram.telekill;

public class GhostModeManager {

    /**
     * Проверяет, нужно ли блокировать отправку статуса "прочитано"
     */
    public static boolean shouldBlockReadStatus(long dialogId) {
        if (!TeleKillConfig.isGhostModeEnabled()) return false;
        if (!TeleKillConfig.isGhostHideReadStatus()) return false;
        return isDialogAffected(dialogId);
    }

    /**
     * Проверяет, нужно ли блокировать отправку статуса "печатает"
     */
    public static boolean shouldBlockTyping(long dialogId) {
        if (!TeleKillConfig.isGhostModeEnabled()) return false;
        if (!TeleKillConfig.isGhostHideTyping()) return false;
        return isDialogAffected(dialogId);
    }

    /**
     * Проверяет, нужно ли блокировать обновление статуса online
     */
    public static boolean shouldBlockOnlineStatus() {
        if (!TeleKillConfig.isGhostModeEnabled()) return false;
        return TeleKillConfig.isGhostHideOnline();
    }

    private static boolean isDialogAffected(long dialogId) {
        switch (TeleKillConfig.getGhostModeType()) {
            case TeleKillConfig.GHOST_TYPE_ALL:
                return true;
            case TeleKillConfig.GHOST_TYPE_GROUPS_ONLY:
                return dialogId < 0; // группы имеют отрицательный id
            case TeleKillConfig.GHOST_TYPE_CONTACTS_ONLY:
            case TeleKillConfig.GHOST_TYPE_EXCEPT_FAVORITES:
            default:
                return true;
        }
    }
}
