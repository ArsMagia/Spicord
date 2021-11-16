package ryo.utils.spicord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ryo.utils.spicord.discord.commands.MinecraftInfoCMD;
import ryo.utils.spicord.discord.commands.PrivateMessageCMD;
import ryo.utils.spicord.discord.listeners.DiscordChatListener;
import ryo.utils.spicord.minecraft.commands.ReplyCMD;
import ryo.utils.spicord.minecraft.commands.SpicordCMD;
import ryo.utils.spicord.minecraft.commands.ToggleCMD;
import ryo.utils.spicord.minecraft.listeners.MinecraftChatListener;
import ryo.utils.spicord.spicord.SpicordManager;

import javax.security.auth.login.LoginException;

public final class Spicord extends JavaPlugin {

    private static Spicord instance;

    private static JDA jda;
    private static Guild guild;
    private static TextChannel channel;

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        instance = this;
        this.reloadConfig();
        this.saveDefaultConfig();

        registerDiscord();
        registerConfig();

        registerCommand("spicord", new SpicordCMD());
        registerCommand("toggle", new ToggleCMD());
        registerCommand("reply", new ReplyCMD());

        registerEvents(
                new MinecraftChatListener()
        );
    }

    private void registerDiscord() {
        String token = config.getString("discord.token");
        long guildID = NumberUtils.toLong(config.getString("discord.guild"));
        long channelID = NumberUtils.toLong(config.getString("discord.channel"));

        try {
            jda = JDABuilder.createDefault(token)
                    .setActivity(Activity.playing("Spicord in " + getServer().getServerName()))
                    .setStatus(OnlineStatus.ONLINE)
                    .setAutoReconnect(true)
                    .addEventListeners(
                            new MinecraftInfoCMD(),
                            new PrivateMessageCMD(),
                            new DiscordChatListener()
                    )
                    .build();
            jda.awaitReady();

            guild = jda.getGuildById(guildID);
            channel = guild.getTextChannelById(channelID);
            //channel.sendMessage("Spicord is now active on **" + getServer().getServerName() + "**!").queue();


        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            getServer().getConsoleSender().sendMessage(
                    ChatColor.RED + "Spicord failed to load. Check your " + ChatColor.GRAY + "config.yml " +
                    ChatColor.RED + "and " + ChatColor.GRAY + "Discord Guild & Channel " + ChatColor.RED + ".");
        }

        //即テスト用
        for (Guild guild : jda.getGuilds()) {
            CommandListUpdateAction commands = guild.updateCommands();
            commands.addCommands(new CommandData("mcinfo", "Get the server info."));
            commands.addCommands(new CommandData("message", "Send a private message.")
                    .addOptions(new OptionData(OptionType.STRING, "text", "Insert your message here.").setRequired(true))
            );
            commands.queue();
        }
    }

    private void registerConfig() {
        boolean tagVisible = config.getBoolean("minecraft.tagvisible");
        SpicordManager.setShowUserTag(tagVisible);
    }


    private void registerCommand(String command, CommandExecutor executor) {
        getCommand(command).setExecutor(executor);
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public void runTask() {
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(this, 1L, 1L);
    }

    public static Spicord getInstance() {
        return instance;
    }

    public static JDA getJDA() {
        return jda;
    }

    public static Guild getGuild() {
        return guild;
    }

    public static TextChannel getChannel() {
        return channel;
    }
}
