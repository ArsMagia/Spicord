package ryo.utils.spicord.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ryo.utils.spicord.spicord.SpicordManager;

public class ReplyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!SpicordManager.isValidDiscordChannel()) return true;
        if (!(sender instanceof Player)) return true;

        // ToDo Discord経由でPMを送ってきた人にPMを返すコマンド

        return false;
    }
}
