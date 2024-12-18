package dev.ufo.console.command;

import lombok.Getter;

@Getter
public abstract class Command {

    public abstract void onCommand(String command, String[] args);
    private final String description;
    private final String usage;
    public Command(String description, String usage) {
        this.description = description;
        this.usage = usage;
    }

}
