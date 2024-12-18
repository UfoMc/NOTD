package dev.ufo.console;

import dev.ufo.data.Date;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Log {

    private static final StringBuilder lines = new StringBuilder();

    static void log(String s) {
        System.out.println(s);

        lines.append(s).append("\n");
    }

    public static void save(String path) {

        String fileName = LocalTime.now().getHour() > 12 ? (LocalTime.now().getHour() - 12) + "pm" : LocalTime.now().getHour() + "am";

        File file = new File(path + Date.today() + File.separator + fileName + ".log");

        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write(lines.toString());

        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
