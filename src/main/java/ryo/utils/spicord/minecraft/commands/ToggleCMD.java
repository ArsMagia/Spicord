package ryo.utils.spicord.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ryo.utils.spicord.spicord.SpicordManager;
import ryo.utils.spicord.spicord.TextUtils;

import java.util.Locale;

public class ToggleCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!SpicordManager.isValidDiscordChannel()) return true;
        if (!sender.isOp()) return true;

        boolean toggle;

        if (args.length >= 1 && (args[0].equals("true") || args[0].equals("false"))) {
            toggle = Boolean.parseBoolean(args[0].toLowerCase());
        }
        else toggle = !SpicordManager.isToggleChat();

        SpicordManager.setToggleChat(toggle);
        sender.sendMessage(TextUtils.PREFIX + "Discord Chat Toggle: " + (toggle ? ChatColor.GREEN.toString() + toggle : ChatColor.RED.toString() + toggle));
        return true;
    }
}
