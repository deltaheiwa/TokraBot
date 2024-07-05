package com.tokra.bot.listeners;

import com.tokra.bot.handlers.DiscordCommandHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandListener extends ListenerAdapter {
    private final DiscordCommandHandler commandHandler = new DiscordCommandHandler();
    private final Map<Long, String> serverPrefixes = new HashMap<>(); // Server-specific prefixes

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String dynamicPrefix = serverPrefixes.getOrDefault(event.getGuild().getIdLong(), "?");
        commandHandler.handleTextCommand(event, dynamicPrefix);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandHandler.handleSlashCommand(event);
    }
}
