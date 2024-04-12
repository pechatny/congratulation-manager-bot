package com.pechatny.congratulation.manager.command;

import java.util.HashMap;

public class CommandsRepository implements Commands {
    HashMap<String, Command> repository = new HashMap<>();

    @Override
    public Command command(String name) {
        return repository.get(name);
    }

    @Override
    public void register(String name, Command command) {
        repository.put(name, command);
    }
}
