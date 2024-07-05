package com.tokra;

import com.tokra.bot.Tokra;
import com.tokra.console.ConsoleThread;

public class Main {
    public static void main(String[] args) {
        Tokra tokra = Tokra.getInstance();
        ConsoleThread consoleThread = new ConsoleThread();

        consoleThread.start();
        tokra.run();
    }
}
