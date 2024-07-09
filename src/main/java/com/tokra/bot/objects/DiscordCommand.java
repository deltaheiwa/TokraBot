package com.tokra.bot.objects;

import com.tokra.bot.Tokra;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public abstract class DiscordCommand {
    public abstract String getName();
    public abstract String getDescription();

    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    };

    public abstract void execute(DiscordCommandContext context);
}
