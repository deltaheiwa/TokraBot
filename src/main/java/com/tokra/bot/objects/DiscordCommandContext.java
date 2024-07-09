package com.tokra.bot.objects;

import com.tokra.bot.enums.DiscordCommandEventType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;
import java.util.stream.*;

public class DiscordCommandContext {
    private final DiscordCommandEventType type;
    private final MessageReceivedEvent textEvent;
    private final SlashCommandInteractionEvent slashEvent;
    private final List<String> args;

    public DiscordCommandContext(MessageReceivedEvent event, List<String> args) {
        this.textEvent = event;
        this.args = args;
        this.slashEvent = null;
        this.type = DiscordCommandEventType.TEXT;
    }

    public DiscordCommandContext(SlashCommandInteractionEvent event) {
        this.slashEvent = event;
        this.textEvent = null;
        this.args = event.getOptions().stream()
                .map(OptionMapping::getAsString)
                .collect(Collectors.toList());
        this.type = DiscordCommandEventType.SLASH;
    }

    public MessageReceivedEvent getTextEvent() {
        return textEvent;
    }

    public SlashCommandInteractionEvent getSlashEvent() {
        return slashEvent;
    }

    public DiscordCommandEventType getType() {
        return type;
    }

    public Guild getGuild() {
        if (type == DiscordCommandEventType.TEXT) {
            return textEvent.getGuild();
        } else {
            return slashEvent.getGuild();
        }
    }

    public void sendMessage(String msg) {
        if (type == DiscordCommandEventType.TEXT) {
            textEvent.getChannel().sendMessage(msg).queue();
        } else {
            slashEvent.reply(msg).queue();
        }
    }


    public List<String> getArgs() {
        return args;
    }
}
