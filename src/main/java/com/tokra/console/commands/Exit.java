package com.tokra.console.commands;

import com.tokra.bot.Tokra;

public class Exit extends ConsoleCommand {
    public Exit() {
        super("exit");
    }

    @Override
    public void execute() {
        Tokra.getInstance().getShardManager().shutdown();
        System.exit(0);
    }
}
