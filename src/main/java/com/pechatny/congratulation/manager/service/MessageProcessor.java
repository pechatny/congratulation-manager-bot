package com.pechatny.congratulation.manager.service;

import com.pechatny.congratulation.manager.bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageProcessor {
    void processMessage(Bot bot, Update update);
}
