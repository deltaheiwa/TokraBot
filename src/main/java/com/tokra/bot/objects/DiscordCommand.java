package com.tokra.bot.objects;

import com.tokra.bot.Tokra;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.List;


public abstract class DiscordCommand {
    protected final Logger logger = LoggerFactory.getLogger(DiscordCommand.class);

    public abstract String getName();
    public abstract String getDescription();
    public abstract List<String> getAliases();


    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    };

    public abstract void execute(DiscordCommandContext context);
}
