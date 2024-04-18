package com.pechatny.congratulation.manager.service.processor;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.command.ParticipantsCommand;
import com.pechatny.congratulation.manager.command.PoetryCommand;
import com.pechatny.congratulation.manager.command.StartCommand;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MessageProcessorLogic implements MessageProcessor {
    private final StateRepository stateRepository;
    public static final String START_COMMAND = "/START";

    public MessageProcessorLogic(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public void processMessage(Bot bot, Long chatId, String message) {
        message = message.trim();
        List<State> states = stateRepository.findStateByChatId(chatId);

        State state;
        if (states.isEmpty()) {
            state = new State(chatId, Status.START, "");
            stateRepository.save(state);
        } else {
            state = states.get(0);
        }
        try {
            if (message.equalsIgnoreCase(START_COMMAND)) {
                state = state.withUpdatedStatus(Status.START);
                var command = new StartCommand(bot, chatId);
                var updatedState = state.withUpdatedStatus(Status.WAIT_FOR_PARTICIPANTS);
                stateRepository.save(updatedState);
                command.execute();

            } else {
                switch (state.getStatus()) {
                    case START -> {
                        var command = new StartCommand(bot, chatId);
                        var updatedState = state.withUpdatedStatus(Status.WAIT_FOR_PARTICIPANTS);
                        stateRepository.save(updatedState);
                        command.execute();
                    }
                    case WAIT_FOR_PARTICIPANTS -> {
                        var command = new ParticipantsCommand(bot, state, message, stateRepository);
                        command.execute();
                    }
                    case WAIT_FOR_POETRY -> {
                        var command = new PoetryCommand(bot, state, message, stateRepository);
                        command.execute();
                    }
                }
            }
        } catch (TelegramApiException e) {
            LoggerFactory.getLogger(MessageProcessorLogic.class).error("Convert operation error! Message: {}", message);
        }
    }
}
