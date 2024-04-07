package com.pechatny.congratulation.manager.service;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.bot.Command;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ParticipantsCommand implements Command {
    private static final String RESPONSE_MESSAGE = """
            Участники приняты, теперь пришли мне поздравление.
            Например:
            У лукоморья дуб зелёный;
            Златая цепь на дубе том:
            И днём и ночью кот учёный
            Всё ходит по цепи кругом;
            """;
    private final Bot bot;
    private final State state;
    private final String message;
    private final StateRepository stateRepository;

    public ParticipantsCommand(Bot bot, State state, String message, StateRepository stateRepository) {
        this.bot = bot;
        this.state = state;
        this.message = message;
        this.stateRepository = stateRepository;
    }

    @Override
    public void execute() throws TelegramApiException {
        var updatedStated = state.updateMessage(message).updateStatus(Status.WAIT_FOR_POETRY);
        stateRepository.save(updatedStated);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(RESPONSE_MESSAGE);
        sendMessage.setChatId(state.getChatId());
        bot.execute(sendMessage);
    }
}
