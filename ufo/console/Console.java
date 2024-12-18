package dev.ufo.console;

public class Console {

    public static void log(String msg) {
        print(
                new LogBuilder()
                        .setType(LogType.NONE)
                        .addDateTime()
                        .addInfo(msg)
                        .build()
        );
    }

    public static void error(String msg) {
        print(
                new LogBuilder()
                        .setType(LogType.ERROR)
                        .addDateTime()
                        .addInfo(msg)
                        .errorLines()
                        .build()
        );
    }

    public static void error(String s, Exception e) {
        print(
                new LogBuilder()
                        .setType(LogType.ERROR)
                        .addDateTime()
                        .addInfo(s + "\n")
                        .addInfo(e.getMessage())
                        .errorLines(e)
                        .build()
        );
    }

    public static void error(Exception e) {
        print(
                new LogBuilder()
                    .setType(LogType.ERROR)
                    .addDateTime()
                    .addInfo(e.getMessage())
                    .errorLines(e)
                    .build()
        );
    }

    public static void warning(String msg) {
        print(
                new LogBuilder()
                    .setType(LogType.WARNING)
                    .addDateTime()
                    .addInfo(msg)
                    .build()
        );
    }

    public static void info(String msg) {
        print(
                new LogBuilder()
                    .setType(LogType.INFO)
                    .addDateTime()
                    .addInfo(msg)
                    .build()
        );
    }

    public static void emptyConsoleSpace(int space) {
        for (int i = 0; i != space; i++) {
            System.out.println();
        }
    }

    public static void print(String s) {
        Log.log(s);
    }

}
