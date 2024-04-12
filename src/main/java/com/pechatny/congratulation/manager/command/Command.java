package com.pechatny.congratulation.manager.command;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.db.State;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command {
    void execute(Bot bot, Long chatId, String message, State state) throws TelegramApiException;

    String name();


}
