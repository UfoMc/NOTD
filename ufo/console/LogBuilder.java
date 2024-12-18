package dev.ufo.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

class LogBuilder {

    private final StringBuilder builder;

    public LogBuilder() {
        this.builder = new StringBuilder();
    }

    public LogBuilder setType(LogType type) {
        this.builder.append(type.getColor());
        return this;
    }

    public LogBuilder addColor(String s) {
        this.builder.append(s);
        return this;
    }

    public LogBuilder addDateTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        builder.append("[");
        builder.append(currentDate);
        builder.append(" ");
        for (int i = 0; i < 8; i++) {
            builder.append(currentTime.toString().charAt(i));
        }
        builder.append(" ");

        builder.append(ZoneId.systemDefault().toString().split("/")[0]);
        builder.append("] ");
        return this;
    }

    public LogBuilder addInfo(String s) {
        builder.append(s);
        return this;
    }

    public LogBuilder errorLines(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();

        for (int i = 2; i != stackTrace.length; i++) {
            builder.append("\n");
            builder.append("|   ");
            builder.append(stackTrace[i]);
        }

        return this;
    }

    public LogBuilder errorLines() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTrace = throwable.getStackTrace();

        for (int i = 2; i != stackTrace.length; i++) {
            builder.append("\n");
            builder.append("|   ");
            builder.append(stackTrace[i]);
        }

        return this;
    }

    public String build() {
        return builder.append(Colors.RESET).toString();
    }

}
