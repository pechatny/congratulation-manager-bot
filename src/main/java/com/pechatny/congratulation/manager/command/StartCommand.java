package com.pechatny.congratulation.manager.command;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.StateRepository;
import com.pechatny.congratulation.manager.db.Status;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommand implements Command {
    public static final String COMMAND_NAME = "START";
    private static final String START_MESSAGE = """
        Передай мне список участников поздравления.
        Например:
        Александр Невский
        Александр Пушкин
        Александр Суворов
        Антон Чехов
        Лев Толстой
        """;
    private final StateRepository stateRepository;

    public StartCommand(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }


    @Override
    public void execute(Bot bot, Long chatId, String message, State state) throws TelegramApiException {
        stateRepository.save(
            state.withUpdatedStatus(Status.WAIT_FOR_PARTICIPANTS)
        );

        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(START_MESSAGE);
        sendMessage.setChatId(chatId);
        bot.execute(sendMessage);
    }

    @Override
    public String name() {
        return COMMAND_NAME;
    }
}
