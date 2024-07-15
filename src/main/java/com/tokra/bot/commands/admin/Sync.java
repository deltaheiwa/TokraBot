package com.tokra.bot.commands.admin;

import com.tokra.bot.Tokra;
import com.tokra.bot.objects.DiscordCommand;
import com.tokra.bot.objects.DiscordCommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sync extends DiscordCommand {
    @Override
    public void execute(DiscordCommandContext context) {
        String testGuildId = Tokra.getInstance().getConfig().get("TESTING_SERVER_ID");

        if (!context.getGuild().getId().equals(testGuildId)) {
            context.sendMessage("This command can only be used in the main server.");
            return;
        }


        List<CommandData> slashCommands = Tokra.getInstance().getCommands().stream()
                .map(DiscordCommand::getCommandData)
                .collect(Collectors.toList());
        Guild testGuild = Tokra.getInstance().getShardManager().getGuildById(testGuildId);
        assert testGuild != null;
        testGuild.updateCommands().addCommands(slashCommands).queue();
        context.sendMessage("Synced commands.");
    }

    @Override
    public String getName() {
        return "sync";
    }

    @Override
    public String getDescription() {
        return "Syncs the slash commands for the server.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("sync-commands");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    }
}
