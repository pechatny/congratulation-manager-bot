package com.pechatny.congratulation.manager.service;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.bot.StartCommand;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import io.micrometer.core.annotation.Counted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class MessageProcessor {
    private final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private final StateRepository stateRepository;
    public static final String START_COMMAND = "/START";

    public MessageProcessor(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Counted(value = "congratulation.manager.bot.messages.counter",description = "Count income messages from bot")
    public void processMessage(Bot bot, Update update) {
        String message = getMessage(update).trim();
        Long chatId = update.getMessage().getChatId();
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
                state = state.updateStatus(Status.START);
                var command = new StartCommand(bot, chatId);
                var updatedState = state.updateStatus(Status.WAIT_FOR_PARTICIPANTS);
                stateRepository.save(updatedState);
                command.execute();

            } else {
                switch (state.getStatus()) {
                    case START -> {
                        var command = new StartCommand(bot, chatId);
                        var updatedState = state.updateStatus(Status.WAIT_FOR_PARTICIPANTS);
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
            logger.error("Convert operation error! Message: {}", message);
        }
    }

    private String getMessage(Update update) {

        return update.getMessage().getText();
    }
}
