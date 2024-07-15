package com.tokra.bot.handlers;

import com.tokra.bot.Tokra;
import com.tokra.bot.commands.admin.SetPrefix;
import com.tokra.bot.commands.admin.Sync;
import com.tokra.bot.commands.info.*;
import com.tokra.bot.objects.DiscordCommand;
import com.tokra.bot.objects.DiscordCommandContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

public class DiscordCommandHandler {
    private final Map<String, DiscordCommand> commands = new HashMap<>();
    private final Map<String, String> aliases = new HashMap<>();
    private final String fixedPrefix = "tk-";

    private void addCommand(DiscordCommand command) {
        String primaryName = command.getName().toLowerCase();
        commands.put(primaryName, command);
        command.getAliases().forEach(alias -> aliases.put(alias.toLowerCase(), primaryName));
    }

    public void fetchCommandsFromBot() {
        Tokra.getInstance().getCommands().forEach(this::addCommand);
    }

    private DiscordCommand getCommandByName(String name) {
        String primaryName = aliases.getOrDefault(name, name);
        return commands.get(primaryName);
    }

    public void handleTextCommand(MessageReceivedEvent event, String dynamicPrefix) {
        String rawMessage = event.getMessage().getContentRaw();
        String prefix = rawMessage.startsWith(fixedPrefix) ? fixedPrefix : dynamicPrefix;

        if (!rawMessage.startsWith(prefix)) {
            return;
        }

        String[] split = rawMessage.substring(prefix.length()).split("\\s+");
        String commandName = split[0].toLowerCase();
        List<String> args = Arrays.asList(split).subList(1, split.length);

        DiscordCommand command = getCommandByName(commandName);

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

    public Map<String, DiscordCommand> getCommands() {
        return commands;
    }
}
