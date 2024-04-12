package com.pechatny.congratulation.manager.command;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import com.pechatny.congratulation.manager.service.greeting.GrBase;
import com.pechatny.congratulation.manager.service.greeting.GrList;
import com.pechatny.congratulation.manager.service.greeting.GrRandom;
import com.pechatny.congratulation.manager.service.greeting.Greeting;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PoetryCommand implements Command {
    public static final String COMMAND_NAME = "POETRY";
    private final StateRepository stateRepository;

    public PoetryCommand(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public void execute(
        Bot bot,
        Long chatId,
        String message,
        State state
    ) throws TelegramApiException {
        Greeting<String> greeting = new GrRandom(
            new GrList(
                new GrBase(
                    state.getMessage(),
                    message
                )
            )
        );

        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(greeting.assignedGreeting());
        sendMessage.setChatId(state.getChatId());
        bot.execute(sendMessage);
        state.withUpdatedStatus(Status.START);
        stateRepository.save(state);
    }

    @Override
    public String name() {
        return COMMAND_NAME;
    }
}
