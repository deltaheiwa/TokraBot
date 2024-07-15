package com.tokra.bot.listeners;

import com.tokra.bot.Tokra;
import com.tokra.bot.handlers.DiscordCommandHandler;
import com.tokra.bot.objects.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class CommandListener extends ListenerAdapter {
    private final DiscordCommandHandler commandHandler = new DiscordCommandHandler();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String dynamicPrefix = event.isFromGuild() ? Tokra.getInstance().getPrefixDatabase().getPrefix(event.getGuild().getId()) : "tk-";

        commandHandler.handleTextCommand(event, dynamicPrefix);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandHandler.handleSlashCommand(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        commandHandler.fetchCommandsFromBot();
    }
}
