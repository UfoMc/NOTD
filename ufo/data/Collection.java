package dev.ufo.data;

import dev.ufo.console.Colors;
import dev.ufo.console.Console;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Collection {

    //DON´T TUCH THE CACHE!!!
    private static final Map<String, Integer> CACHE = new HashMap<>();
    //Path leading to the file where the past sequences are being stored
    public static String FILE_PATH = "C:/Users/matga/IdeaProjects/NOTD/";

    public Collection() {
    }

    public static void definePast(Date d, int i) {
        CACHE.put(d.toString(), i);
    }

    public static void setCurrent(int i) {
        CACHE.put(Date.today().toString(), i);
    }

    public static int search(Date d) {
        return CACHE.getOrDefault(d.toString(), 404);
    }

    public static String search(int i) {
        return Optional.ofNullable(CACHE.containsValue(i) ? getKeyByValue(i) : null)
                .orElse("404");
    }

    private static String getKeyByValue(int value) {
        for (Map.Entry<String, Integer> entry : CACHE.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void loadCache() {
        Console.log("Cache initializing...");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + "table"))){

            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                i++;
                String[] splitLine = line.split("#");
                if (splitLine.length != 2) {
                    Console.warning("Please check on the table line " + Colors.CYAN + i);
                    continue;
                }
                try {
                    CACHE.put(splitLine[0], Integer.parseInt(splitLine[1]));
                } catch (Exception e) {
                    Console.warning("Could not parse " + splitLine[1] + " as an integer");
                }
            }

            Console.log("Cache was filled up successfully");

        } catch (Exception e){
            Console.warning("Cache startup failed!");
        }

    }

    public static void saveCache() {
        Console.log("Cache saving...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + "table"))) {

            CACHE.forEach((date, value) -> {

                try {
                    writer.write(date + "#" + value);
                    writer.newLine();
                } catch (IOException e) {
                    Console.warning("There was something wrong trying to save the cache. Trying eigen!");
                    saveCache();
                }

            });

        } catch (Exception e) {
            Console.warning("Cache could not be saved!");
        }
    }

}
