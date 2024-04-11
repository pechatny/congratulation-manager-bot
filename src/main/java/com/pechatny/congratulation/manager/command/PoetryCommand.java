package com.pechatny.congratulation.manager.command;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import com.pechatny.congratulation.manager.service.greeting.GrList;
import com.pechatny.congratulation.manager.service.greeting.GrRandom;
import com.pechatny.congratulation.manager.service.greeting.GrString;
import com.pechatny.congratulation.manager.service.greeting.Greeting;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PoetryCommand implements Command {
    private final Bot bot;
    private final State state;
    private final String message;
    private final StateRepository stateRepository;

    public PoetryCommand(Bot bot, State state, String message, StateRepository stateRepository) {
        this.bot = bot;
        this.state = state;
        this.message = message;
        this.stateRepository = stateRepository;
    }

    @Override
    public void execute() throws TelegramApiException {
        Greeting<String> greeting = new GrRandom(
            new GrList(
                new GrString(
                    state.getMessage(),
                    message
                )
            )
        );

        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(greeting.assignedGreeting());
        sendMessage.setChatId(state.getChatId());
        bot.execute(sendMessage);
        state.updateStatus(Status.START);
        stateRepository.save(state);
    }
}
