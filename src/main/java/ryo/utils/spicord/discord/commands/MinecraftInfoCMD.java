package ryo.utils.spicord.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ryo.utils.spicord.Spicord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinecraftInfoCMD extends ListenerAdapter {
    private final String COMMAND = "mcinfo";

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getUser().isBot() || event.getUser().isSystem()) return;
        if (event.isAcknowledged()) return;
        SelfUser selfUser = event.getJDA().getSelfUser();

        if (event.getName().equalsIgnoreCase(COMMAND)) {
            String players = "";
            for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                if (i % 3 == 0) players += list.get(i).getName() + " ";
                else players += list.get(i).getName() + "\n";
            }

            event.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.GRAY)
                    .setTitle("=====[ " + Spicord.getInstance().getServer().getServerName() + " ]=====")
                    .setDescription("Minecraft Server Info")
                    .setAuthor(selfUser.getName(), null, selfUser.getAvatarUrl())
                    .addField("Server Version", Bukkit.getServer().getBukkitVersion(), false)
                    .addField("Online Players", players, false)
                    .build()
            ).setEphemeral(true).queue();
        }

    }
}
