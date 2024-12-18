package dev.ufo.console;

import java.lang.reflect.Field;

public class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public void printTest() {

        StringBuilder s = new StringBuilder();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                s.append(RESET).
                        append(field.get(null)).
                        append(field.getName()).append("\n");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(s);

    }

}

enum LogType {

    INFO(Colors.PURPLE),
    ERROR(Colors.RED),
    WARNING(Colors.YELLOW),
    NONE(Colors.WHITE);

    private final String color;

    LogType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
