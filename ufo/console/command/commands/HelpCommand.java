package dev.ufo.console.command.commands;

import dev.ufo.console.command.Command;
import dev.ufo.console.command.CommandHandler;

public class HelpCommand extends Command {

    private static final int DISTANCE_FROM_TABLE_TO_ALIAS = 5;
    private static final int TOTAL_DISTANCE = 10;

    public HelpCommand() {
        super("helps you a lot :D", "<alias>");
    }

    @Override
    public void onCommand(String command, String[] args) {

        System.out.println("############################################");
        CommandHandler.commands.forEach((aliases, commandImp) -> {

            System.out.println(aliases.get(0) + emptySpace(aliases.get(0)) + commandImp.getDescription());

            for (int i = 1; i != aliases.size(); i++) {
                System.out.println(aliases.get(i) + emptySpace(aliases.get(i)));
            }

            System.out.println(emptySpace("") + commandImp.getUsage());
            System.out.println();

        });

    }

    public static String emptySpace(String s) {

        StringBuilder empty = new StringBuilder();

        for (int i = 0; i != TOTAL_DISTANCE + getLongestCommand() - s.length(); i++) {
            if (i == getLongestCommand() - s.length() + DISTANCE_FROM_TABLE_TO_ALIAS) {
                empty.append("|");
                continue;
            }
            empty.append(" ");
        }

        return empty.toString();

    }

    public static int getLongestCommand() {

        final int[] i = {0};

        CommandHandler.commands.forEach((aliases, commandImp) ->{

            for (String s : aliases) {
                if (s.length() > i[0]) {
                    i[0] = s.length();
                }
            }

        });

        return i[0];

    }

}
