package com.pechatny.congratulation.manager.service.message;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.service.processor.MessageProcessor;

public class MsgBase implements Message {
    private final Long chatId;
    private final String message;

    public MsgBase(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public void process(Bot bot, MessageProcessor processor) {
        processor.processMessage(bot, chatId, message);
    }
}
