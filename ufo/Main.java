package dev.ufo;

import dev.ufo.console.Colors;
import dev.ufo.console.Console;
import dev.ufo.console.Log;
import dev.ufo.console.command.CommandHandler;
import dev.ufo.data.Collection;
import dev.ufo.data.Date;
import dev.ufo.rest.RestApi;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    public static List<Runnable> SHUTDOWN_HOOKS = new ArrayList<>();
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    /*initialize the project*/
    public static void main(String[] args) {

        Console.info("System is starting up...");

        Collection.loadCache();
        startCounter();
        addShutDownHook();
        generateNOTD();
        RestApi.init();
        CommandHandler.init();

        Console.info("System is started up and ready to go!");

    }

    private static void addShutDownHook() {
        Console.log("Shutdown hooks are being added...");
        /*space to add hooks*/
        SHUTDOWN_HOOKS.add(()-> {
            Console.emptyConsoleSpace(100);
            Console.info("Start shutting down...");
        });
        SHUTDOWN_HOOKS.add(Collection::saveCache);
        SHUTDOWN_HOOKS.add(SCHEDULER::close);
        SHUTDOWN_HOOKS.add(() -> Log.save(Collection.FILE_PATH + File.separator + "logs" + File.separator));

        /*execute the shutdown hooks*/
        for (Runnable r : SHUTDOWN_HOOKS) {
            Runtime.getRuntime().addShutdownHook(new Thread(r));
        }
    }

    private static void startCounter() {
        Console.log("Initializing counter...");

        SCHEDULER.scheduleAtFixedRate(
                Main::generateNOTD, calculateInitialDelay()/*0*/, 24, TimeUnit.HOURS
        );

        Console.log("Counter is set up!");
    }

    //public static void addSystemProperties() {
    //    Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
    //        Console.error(new Exception(throwable));
    //    });
    //}

    private static long calculateInitialDelay() {
        long currentTime = System.currentTimeMillis();
        long nextMidnight = (currentTime / 86400000 + 1) * 86400000;

        Console.log("Next schedule will be in: ~" + Colors.PURPLE + TimeUnit.MILLISECONDS.toHours(nextMidnight - currentTime) + "h" + Colors.RESET);
        return nextMidnight - currentTime;
    }

    private static void generateNOTD() {
        if (Objects.equals(Collection.search(Date.today()), 404)) {
            Console.log("Generating the new NOTD");

            int random = random();
            Collection.setCurrent(random);

            Console.log("The new NOTD for " + Date.today().toFormalString() + " is " + Colors.CYAN + random);
        } else {
            Console.warning("Eighter an importante error occurred or the server was just started. \nIf it was, you can ignore this!");
        }
    }

    public static void generateNOTD(Date date) {
        if (Objects.equals(Collection.search(date), 404)) {

            int random = random();
            Collection.definePast(date, random);

            Console.log("The new NOTD for " + date + " is " + Colors.CYAN + random);
        } else {
            Console.warning("Eighter an importante error occurred or the server was just started. \nIf it was, you can ignore this!");
        }
    }

    private static int random() {
        return ThreadLocalRandom.current().nextInt(0, 1000000000);
        //return Objects.equals(Collection.search(i), "404") ? i : random();
    }

}