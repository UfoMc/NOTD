package dev.ufo.console.command;

import dev.ufo.console.Console;
import dev.ufo.console.command.commands.GeneratePastCommand;
import dev.ufo.console.command.commands.HelpCommand;
import dev.ufo.console.command.commands.SearchCommand;

import java.util.*;

public class CommandHandler {

    public static Map<List<String>, Command> commands = new HashMap<>();

    public static void executeCommand(String command) {

        String[] splitCommand = command.split(" ");

        commands.forEach((names, imp) -> {
            if (names.contains(splitCommand[0])) {

                String[] args = null;

                if (splitCommand.length > 1) {
                    args = new String[splitCommand.length - 1];
                    System.arraycopy(splitCommand, 1, args, 0, splitCommand.length - 1);
                }

                imp.onCommand(splitCommand[0], args);
            }
        });

    }

    public static void init() {
        Console.log("Enabling the command system...");

        new Thread(CommandHandler::enableScanner).start();

        addCommand(new HelpCommand(), "?", "help");
        addCommand(new GeneratePastCommand(), "genpast");
        addCommand(new SearchCommand(), "search", "get");

        Console.log("Command system is running");
    }

    public static void addCommand(Command c, String s, String... aliases) {
        List<String> alias = new ArrayList<>(Arrays.stream(aliases).toList());
        alias.add(s);
        commands.put(alias, c);
    }

    public static void enableScanner() {

        Scanner scanner = new Scanner(System.in);
        String line;
        System.out.print("# ");
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();

            System.out.print("# ");

            if (line.isEmpty()) {
                continue;
            }

            executeCommand(line);
            System.out.print("# ");
        }
    }

}
