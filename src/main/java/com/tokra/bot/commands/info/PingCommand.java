package com.tokra.bot.commands.info;

import com.tokra.bot.Tokra;
import com.tokra.bot.objects.DiscordCommand;
import com.tokra.bot.objects.DiscordCommandContext;

public class PingCommand implements DiscordCommand {
    @Override
    public void execute(DiscordCommandContext context) {
        String pongMessage = String.format("Pong %sms", Tokra.getInstance().getShardManager().getShards().get(0).getGatewayPing());
        if (context.getTextEvent() != null) {
            context.getTextEvent().getChannel().sendMessage(pongMessage).queue();
        } else {
            context.getSlashEvent().reply(pongMessage).queue();
        }
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Pings the bot.";
    }

}
