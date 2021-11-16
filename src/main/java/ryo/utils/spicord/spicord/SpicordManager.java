package ryo.utils.spicord.spicord;

import ryo.utils.spicord.Spicord;

import java.util.Map;

public class SpicordManager {

    private static boolean toggleChat = true;
    private static boolean showUserTag = true;

    public static boolean isToggleChat() {
        return toggleChat;
    }

    public static void setToggleChat(boolean toggle) {
        toggleChat = toggle;
    }

    public static boolean isShowUserTag() {
        return showUserTag;
    }

    public static void setShowUserTag(boolean toggle) {
        showUserTag = toggle;
    }

    public static boolean isValidDiscordChannel() {
        return Spicord.getJDA() != null && Spicord.getGuild() != null && Spicord.getChannel() != null;
    }


}
