package com.pechatny.congratulation.manager.command;

public interface Commands {
    Command command(String name);

    void register(String name, Command command);
}
