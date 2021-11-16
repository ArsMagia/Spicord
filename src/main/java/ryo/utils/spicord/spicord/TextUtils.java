package ryo.utils.spicord.spicord;

import org.bukkit.ChatColor;

public class TextUtils {

    public static final String PREFIX = ChatColor.DARK_GREEN + "[" + ChatColor.GRAY + "Discord" + ChatColor.DARK_GREEN + "] " + ChatColor.RESET;
    public static final String PREFIXPM = ChatColor.LIGHT_PURPLE + "[" + ChatColor.GRAY + "DiscordPM" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.GRAY;

    public static String filterForMinecraft(String s) {
        if (s.charAt(0) == '/') s = "[" + s.charAt(0) + "]" + s.substring(1);
        return s;
    }

    public static String filterForDiscord(String s) {
        switch (s.charAt(0)) {
            case '/':
            case ',':
            case '.':
            case '!':
            case '?':
                s =  "[" + s.charAt(0) + "]" + s.substring(1);
        }
        return s.replace("@", "#");
    }

    public static String convertForMinecraft(String[] args) {
        String chatMinecraft = "";
        for (String s : args) {
            chatMinecraft += filterForMinecraft(s) + " ";
        }
        return chatMinecraft;
    }

    public static String convertForDiscord(String[] args) {
        String chatDiscord = "";
        for (String s : args) {
            chatDiscord += filterForDiscord(s) + " ";
        }
        return chatDiscord;
    }
}
