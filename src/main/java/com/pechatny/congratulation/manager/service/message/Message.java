package com.pechatny.congratulation.manager.service.message;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.service.processor.MessageProcessor;

public interface Message {
    void process(Bot bot, MessageProcessor processor);
}
