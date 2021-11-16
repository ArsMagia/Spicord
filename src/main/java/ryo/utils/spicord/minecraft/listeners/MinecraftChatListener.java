package ryo.utils.spicord.minecraft.listeners;

import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ryo.utils.spicord.Spicord;
import ryo.utils.spicord.spicord.SpicordManager;
import ryo.utils.spicord.spicord.TextUtils;

public class MinecraftChatListener implements Listener {
    private final String prefix = TextUtils.PREFIX;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (Spicord.getJDA() == null || Spicord.getGuild() == null || Spicord.getChannel() == null) return;
        if (!SpicordManager.isToggleChat()) return;
        Player player = event.getPlayer();
        event.setCancelled(true);

        TextChannel channel = Spicord.getChannel();

        for (Player target : Bukkit.getOnlinePlayers()) {
            target.sendMessage(prefix + player.getName() + ": " + event.getMessage());
        }
        channel.sendMessage("[" + Spicord.getInstance().getServer().getServerName() + "] " +
                "**" + player.getName() + "**: " + TextUtils.filterForDiscord(event.getMessage())).queue();
    }
}
