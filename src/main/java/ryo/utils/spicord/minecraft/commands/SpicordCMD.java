package ryo.utils.spicord.minecraft.commands;

import net.dv8tion.jda.api.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ryo.utils.spicord.Spicord;
import ryo.utils.spicord.spicord.SpicordManager;
import ryo.utils.spicord.spicord.TextUtils;

public class SpicordCMD implements CommandExecutor {
    private final String prefix = TextUtils.PREFIX;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!SpicordManager.isValidDiscordChannel()) return true;
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(prefix + "/spicord <messages...>");
            return true;
        }

        TextChannel channel = Spicord.getChannel();

        for (Player target : Bukkit.getOnlinePlayers()) {
            target.sendMessage(prefix + player.getName() + ": " + TextUtils.convertForMinecraft(args));
        }
        channel.sendMessage("**" + player.getName() + "**: " + TextUtils.convertForDiscord(args)).queue();
        return true;

    }
}
