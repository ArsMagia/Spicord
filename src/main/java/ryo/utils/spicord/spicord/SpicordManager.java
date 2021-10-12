package ryo.utils.spicord.spicord;

import ryo.utils.spicord.Spicord;

public class SpicordManager {

    private static boolean asyncChat = true;

    public static boolean isAsyncChat() {
        return asyncChat;
    }

    public static void setAsyncChat(boolean toggle) {
        asyncChat = toggle;
    }

    public static boolean isValidDiscordChannel() {
        return Spicord.getJDA() != null && Spicord.getGuild() != null && Spicord.getChannel() != null;
    }
}
