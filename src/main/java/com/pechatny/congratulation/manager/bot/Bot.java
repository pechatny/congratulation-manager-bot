package com.pechatny.congratulation.manager.bot;

import com.pechatny.congratulation.manager.service.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Bot extends TelegramLongPollingBot {
    private final Logger logger = LoggerFactory.getLogger(Bot.class);
    private final BotProperties props;
    private final MessageProcessor processor;

    Bot(BotProperties props, MessageProcessor processor) {
        super(props.token());
        this.props = props;
        this.processor = processor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Message received!");
        if (update.hasMessage()) {
            processor.processMessage(this, update);
        }
    }

    @Override
    public String getBotUsername() {
        return props.name();
    }
}
