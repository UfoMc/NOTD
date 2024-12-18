package dev.ufo.console.command.commands;

import dev.ufo.Main;
import dev.ufo.console.command.Command;
import dev.ufo.data.Date;

public class GeneratePastCommand extends Command {
    public GeneratePastCommand() {
        super("with this command you can generate numbers from the past", "<alias> <start date> <end date> [date is yyyy.mm.dd]");
    }

    @Override
    public void onCommand(String command, String[] args) {

        if (!(args.length == 2)) return;

        Date start = Date.of(args[0]);
        Date end = Date.of(args[1]);

        for (int j = start.getYear(); j != end.getYear() + 1; j++) {

            for (int m = 1; m != 13; m++) {

                if (j == end.getYear() && m == end.getMonth() + 1) {
                    break;
                }

                if (j == start.getYear() && m < start.getMonth()) {
                    continue;
                }

                for (int d = 1; d != 32; d++) {

                    if (m == end.getMonth() && d == end.getDay() + 1) {
                        break;
                    }

                    if (m == start.getMonth() && d < start.getDay()) {
                        continue;
                    }

                    Date date = new Date(j, m, d);
                    //System.out.println(date);
                    Main.generateNOTD(date);

                }

            }

        }

    }
}
