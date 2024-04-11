package com.pechatny.congratulation.manager.service.processor;

import com.pechatny.congratulation.manager.bot.Bot;

public interface MessageProcessor {
    void processMessage(Bot bot, Long chatId, String message);
}
