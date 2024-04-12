package com.pechatny.congratulation.manager.command;

import com.pechatny.congratulation.manager.db.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommandsConfiguration {

    final
    List<Command> commandList;

    public CommandsConfiguration(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Bean
    Commands commands() {
        var defaultCommands = defaultCommandsRepository();
        var commands = new CommandsRepository();
        commands.register(
            Status.START.name(),
            defaultCommands.command(StartCommand.COMMAND_NAME)
        );
        commands.register(
            Status.WAIT_FOR_PARTICIPANTS.name(),
            defaultCommands.command(ParticipantsCommand.COMMAND_NAME)
        );
        commands.register(
            Status.WAIT_FOR_POETRY.name(),
            defaultCommands.command(PoetryCommand.COMMAND_NAME)
        );

        return commands;
    }

    private Commands defaultCommandsRepository() {
        var commands = new CommandsRepository();
        for (Command command : commandList) {
            commands.register(command.name(), command);
        }

        return commands;
    }
}
