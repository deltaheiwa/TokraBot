package com.tokra.bot;

import com.tokra.bot.listeners.*;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

public class Tokra {
    private final static Logger logger = LoggerFactory.getLogger(Tokra.class);
    private final Dotenv config;
    private static Tokra instance = null;

    private boolean isRunning = false;
    private final ShardManager shardManager;

    private Tokra() {
        config = Dotenv.configure().load();
        String token = config.get("DISCORD_TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.addEventListeners(new CommandListener());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Stargate"));
        EnumSet<GatewayIntent> intents = GatewayIntent.getIntents(GatewayIntent.DEFAULT);
        intents.add(GatewayIntent.MESSAGE_CONTENT);
        intents.add(GatewayIntent.GUILD_MEMBERS);
        intents.add(GatewayIntent.GUILD_PRESENCES);
        builder.setEnabledIntents(intents);
        this.shardManager = builder.build(false);
    }

    public static Tokra getInstance() {
        if (instance == null) {
            instance = new Tokra();
        }
        return instance;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public void run() {
        if (isRunning) {
            logger.warn("Tokra is already running");
            return;
        }
        try {
            shardManager.login();
            isRunning = true;
        } catch (InvalidTokenException e) {
            logger.error("Invalid token provided", e);
        }
        logger.info("I have logged in successfully as " + shardManager.getShards().get(0).getSelfUser().getName());
    }
}
