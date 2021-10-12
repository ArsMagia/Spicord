package ryo.utils.spicord.discord.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ryo.utils.spicord.Spicord;
import ryo.utils.spicord.spicord.SpicordManager;
import ryo.utils.spicord.spicord.TextUtils;

public class DiscordChatListener extends ListenerAdapter {
    private final String prefix = TextUtils.PREFIX;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!SpicordManager.isValidDiscordChannel()) return;
        if (event.isWebhookMessage() || event.getAuthor().isBot() || event.getAuthor().isSystem()) return;
        if (event.getMember().getUser() == event.getJDA().getSelfUser()) return;
        if (!SpicordManager.isAsyncChat()) return;

        TextChannel channel = Spicord.getChannel();

        if (event.getChannel().getIdLong() == channel.getIdLong()) {
            Member member = event.getMember();
            String nickName = member.getEffectiveName();
            String asTag = member.getUser().getAsTag();
            String message = event.getMessage().getContentDisplay();


            for (Player target : Bukkit.getOnlinePlayers()) {
                target.sendMessage(prefix + nickName + "(" + asTag + "): " + TextUtils.filterForMinecraft(message));
            }
            event.getMessage().addReaction("\uD83D\uDCE4").queue(); // :outbox_tray:
        }
    }
}
