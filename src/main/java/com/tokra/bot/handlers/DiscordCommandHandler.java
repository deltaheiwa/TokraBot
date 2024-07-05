package com.tokra.bot.handlers;

import com.tokra.bot.commands.info.*;
import com.tokra.bot.objects.DiscordCommand;
import com.tokra.bot.objects.DiscordCommandContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DiscordCommandHandler {
    private final Map<String, DiscordCommand> commands = new HashMap<>();
    private final String fixedPrefix = "tk-";

    public DiscordCommandHandler() {
        addCommand(new PingCommand());
    }

    private void addCommand(DiscordCommand command) {
        commands.put(command.getName(), command);
    }

    public void handleTextCommand(MessageReceivedEvent event, String dynamicPrefix) {
        String rawMessage = event.getMessage().getContentRaw();
        String prefix = rawMessage.startsWith(fixedPrefix) ? fixedPrefix : dynamicPrefix;

        if (!rawMessage.startsWith(prefix)) {
            return;
        }

        String[] split = rawMessage.substring(prefix.length()).split("\\s+");
        String commandName = split[0];
        List<String> args = Arrays.asList(split).subList(1, split.length);

        DiscordCommand command = commands.get(commandName.toLowerCase());

        if (command != null) {
            command.execute(new DiscordCommandContext(event, args));
        }
    }

    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        DiscordCommand command = commands.get(event.getName().toLowerCase());

        if (command != null) {
            command.execute(new DiscordCommandContext(event));
        }
    }
}
