package com.pechatny.congratulation.manager.command;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command {
    void execute() throws TelegramApiException;
}
