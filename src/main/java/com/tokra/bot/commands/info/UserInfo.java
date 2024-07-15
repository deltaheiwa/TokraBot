package com.tokra.bot.commands.info;

import com.tokra.bot.Tokra;
import com.tokra.bot.config.BotConfig;
import com.tokra.bot.objects.DiscordCommand;
import com.tokra.bot.objects.DiscordCommandContext;
import com.tokra.util.TimestampUtil;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.List;

public class UserInfo extends DiscordCommand {
    @Override
    public String getName() {
        return "user-info";
    }

    @Override
    public String getDescription() {
        return "Gets information about a user.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("userinfo");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription()).addOption(OptionType.USER, "user", "The user to get information about.", false);
    }

    @Override
    public void execute(DiscordCommandContext context) {
        String arg = context.getArg(0);
        Member member;
        if (arg != null) {
            member = context.getMentionedMembers().stream().findFirst().orElse(null);
            if (member != null) {
                MessageEmbed embed = createEmbed(member);
                context.sendMessage("", embed);
                return;
            }

            User user = Tokra.getInstance().getShardManager().getUserById(arg);
            user = user != null ? user : Tokra.getInstance().getShardManager().retrieveUserById(arg).complete();
            if (user != null) {
                MessageEmbed embed = createEmbed(user);
                context.sendMessage("", embed);
                return;
            }

            context.sendMessage("User not found.");
            return;
        } else {
            member = context.getAuthorMember();
        }
        MessageEmbed embed = member != null ? createEmbed(member) : createEmbed(context.getAuthorUser());
        context.sendMessage("", embed);
    }

    private MessageEmbed createEmbed(Member member) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Info about user");
        eb.setDescription("User: " + member.getAsMention());
        eb.setColor(member.getColor());
        eb.setThumbnail(member.getUser().getAvatarUrl());

        eb.addField("Username", member.getUser().getName(), true);
        if (member.getNickname() != null) {
            eb.addField("Nickname", member.getNickname(), true);
        }
        eb.addField("ID", member.getId(), true);

        eb.addField("Status", StringUtils.capitalize(member.getOnlineStatus().getKey()), true);
        eb.addField("Server status", getServerStatus(member), false);

        eb.addField("Created", TimestampUtil.getRelativeTimestamp(member.getTimeCreated()), true);
        eb.addField("Joined", TimestampUtil.getRelativeTimestamp(member.getTimeJoined()), true);
        return eb.build();
    }

    private MessageEmbed createEmbed(User user) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Info about user");
        eb.setDescription("User: " + user.getAsMention());
        eb.setColor(BotConfig.getBaseEmbedColor());
        eb.setThumbnail(user.getAvatarUrl());

        eb.addField("Username", user.getName(), true);
        eb.addField("ID", user.getId(), false);

        eb.addField("Created", TimestampUtil.getRelativeTimestamp(user.getTimeCreated()), true);
        return eb.build();
    }

    private String getServerStatus(Member member) {
        if (member.isOwner()) {
            return "Owner";
        } else if (member.isBoosting()) {
            return "Booster";
        } else if (member.hasPermission(Permission.ADMINISTRATOR)) {
            return "Administrator";
        } else if (member.hasPermission(Permission.MANAGE_SERVER)) {
            return "Server manager";
        } else {
            return "Member";
        }
    }
}
