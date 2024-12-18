package dev.ufo.console.command.commands;

import dev.ufo.console.command.Command;
import dev.ufo.data.Collection;
import dev.ufo.data.Date;

public class SearchCommand extends Command {
    public SearchCommand() {
        super("search for numbers or dates in our database", "<alias> <yyyy.mm.dd> or <number>");
    }

    @Override
    public void onCommand(String command, String[] args) {

        if (!(args.length == 1)) return;

        try {
            int i = Integer.parseInt(args[0]);
            System.out.println(Collection.search(i));
        } catch (Exception e){
            System.out.println(Collection.search(Date.of(args[0])));
        }

    }
}
