package com.pechatny.congratulation.manager.service;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.bot.Command;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
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
        ParticipantsSplitter participantSplitter = new ParticipantsSplitter();
        String participants = state.getMessage();

        var greeting = participantSplitter.splitParticipants(participants, message);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(greeting.toString());
        sendMessage.setChatId(state.getChatId());
        bot.execute(sendMessage);
        state.updateStatus(Status.START);
        stateRepository.save(state);

    }
}
