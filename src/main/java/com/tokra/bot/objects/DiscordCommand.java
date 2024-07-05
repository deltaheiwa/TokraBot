package com.tokra.bot.objects;

public interface DiscordCommand {
    void execute(DiscordCommandContext context);
    String getName();
    String getDescription();
}
