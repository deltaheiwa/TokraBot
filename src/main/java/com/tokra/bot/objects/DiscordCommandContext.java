package com.tokra.bot.objects;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;
import java.util.stream.*;

public class DiscordCommandContext {
    private final MessageReceivedEvent textEvent;
    private final SlashCommandInteractionEvent slashEvent;
    private final List<String> args;

    public DiscordCommandContext(MessageReceivedEvent event, List<String> args) {
        this.textEvent = event;
        this.args = args;
        this.slashEvent = null;
    }

    public DiscordCommandContext(SlashCommandInteractionEvent event) {
        this.slashEvent = event;
        this.textEvent = null;
        this.args = event.getOptions().stream()
                .map(OptionMapping::getAsString)
                .collect(Collectors.toList());
    }

    public MessageReceivedEvent getTextEvent() {
        return textEvent;
    }

    public SlashCommandInteractionEvent getSlashEvent() {
        return slashEvent;
    }

    public List<String> getArgs() {
        return args;
    }
}
