package ryo.utils.spicord.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ryo.utils.spicord.spicord.SpicordManager;
import ryo.utils.spicord.spicord.TextUtils;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrivateMessageCMD extends ListenerAdapter {
    private final String PREFIX = TextUtils.PREFIXPM;
    private final String COMMAND = "message";
    private final String TEXT = "text";
    private static Map<User, String> pendingMessage = new HashMap<>();

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getUser().isBot() || event.getUser().isSystem()) return;
        if (event.isAcknowledged()) return;
        if (event.getOption(TEXT) == null) return;
        // SelfUser selfUser = event.getJDA().getSelfUser();
        User user = event.getUser();

        if (event.getName().equalsIgnoreCase(COMMAND)) {
            OptionMapping option = event.getOption(TEXT);
            String text = option.getAsString();

            // Embed
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.GRAY)
                    .setTitle("Send a private message...")
                    .setDescription("```" + text + "```")
                    .setAuthor(user.getName(), null, user.getAvatarUrl());

            // SelectionMenu
            SelectionMenu.Builder menuBuilder = SelectionMenu.create("message");
            menuBuilder.setPlaceholder("Select a Player");
            menuBuilder.setRequiredRange(1, 25); // 25枠が限界らしい
            Bukkit.getOnlinePlayers().forEach(player -> menuBuilder.addOption(player.getName(), "message-" + player.getUniqueId(), "PM to " + player.getName()));
            if (Bukkit.getOnlinePlayers().size() == 0) {
                menuBuilder.addOption("No Players Found", "message-none", "");
                menuBuilder.setPlaceholder("No Players Found");
                menuBuilder.setDisabled(true);
            }

            // Build
            MessageEmbed embed = embedBuilder.build();
            SelectionMenu menu = menuBuilder.build();

            pendingMessage.put(event.getUser(), text);
            event.replyEmbeds(embed).addActionRow(menu).queue();
        }
    }

    @Override
    public void onSelectionMenu(SelectionMenuEvent event) {
        List<SelectOption> selected = event.getInteraction().getSelectedOptions();
        SelectionMenu menu = event.getInteraction().getSelectionMenu();
        Member member = event.getMember();
        User user = event.getUser();
        List<Player> targetPlayers = new ArrayList<>();

        if (member == null || selected == null || menu == null) return;
        if (!pendingMessage.containsKey(event.getUser())) return;

        // 送信者の詳細
        String nickName = member.getEffectiveName();
        String asTag = SpicordManager.isShowUserTag() ? "(" + user.getAsTag() + ")" : "";

        // SelectionMenuを無効化する
        menu = menu.asDisabled();
        event.getMessage().editMessageComponents(ActionRow.of(menu)).queue();

        // メッセージを指定プレイヤー達に送る
        for (SelectOption option : selected) {
            UUID uuid = UUID.fromString(option.getValue().replaceFirst("message-", ""));
            Player target = Bukkit.getPlayer(uuid);
            if (target == null) return;
            targetPlayers.add(target);
            target.sendMessage(PREFIX + nickName + asTag + ": " + pendingMessage.get(event.getUser()));
        }

        // 送信完了メッセージ
        String players = "";
        for (int i = 0; i < targetPlayers.size(); i++) {
            if (i % 3 == 0) players += targetPlayers.get(i).getName() + " ";
            else players += targetPlayers.get(i).getName() + "\n";
        }

        MessageEmbed sentMessage = new EmbedBuilder()
                .setColor(Color.GRAY)
                .setTitle("\uD83D\uDCE4" + "Your message has successfully been sent!")
                .setDescription("```" + pendingMessage.get(event.getUser()) + "```")
                .setAuthor(user.getName(), null, user.getAvatarUrl()).addField("Message receiver", players, false)
                .build();

        if (event.getMessage() != null) {
            event.replyEmbeds(sentMessage).setEphemeral(true).queue(); // :outbox_tray:
            event.getMessage().delete().queueAfter(1, TimeUnit.SECONDS);
        }
    }
}
