package com.pechatny.congratulation.manager.service.processor;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.command.Commands;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MessageProcessorLogic implements MessageProcessor {
    private final StateRepository stateRepository;
    private final Commands commands;
    public static final String START_COMMAND = "/START";

    public MessageProcessorLogic(StateRepository stateRepository, Commands commands) {
        this.stateRepository = stateRepository;
        this.commands = commands;
    }

    public void processMessage(Bot bot, Long chatId, String message) {
        message = message.trim();
        List<State> states = stateRepository.findStateByChatId(chatId);

        State state;
        if (states.isEmpty() || message.equalsIgnoreCase(START_COMMAND)) {
            state = new State(chatId, Status.START, "");
            stateRepository.save(state);
        } else {
            state = states.get(0);
        }

        try {
            commands.command(state.getStatus().name()).execute(bot, chatId, message, state);
        } catch (TelegramApiException e) {
            LoggerFactory.getLogger(MessageProcessorLogic.class).error("Convert operation error! Message: {}", message);
        }
    }
}
