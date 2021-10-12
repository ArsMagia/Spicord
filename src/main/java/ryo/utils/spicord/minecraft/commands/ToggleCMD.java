package ryo.utils.spicord.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ryo.utils.spicord.spicord.SpicordManager;
import ryo.utils.spicord.spicord.TextUtils;

public class ToggleCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!SpicordManager.isValidDiscordChannel()) return true;
        if (!sender.isOp()) return true;

        boolean toggle;

        if (args.length == 1 && (args[0].equals("true") || args[0].equals("false"))) {
            toggle = Boolean.parseBoolean(args[0].toLowerCase());
        }
        else toggle = !SpicordManager.isAsyncChat();

        SpicordManager.setAsyncChat(toggle);
        sender.sendMessage(TextUtils.PREFIX + "AsyncChat Toggle: " + (toggle ? ChatColor.GREEN.toString() + toggle : ChatColor.RED.toString() + toggle));
        return true;
    }
}
