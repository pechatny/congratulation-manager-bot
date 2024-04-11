package com.pechatny.congratulation.manager.bot;

import com.pechatny.congratulation.manager.service.message.MsgBase;
import com.pechatny.congratulation.manager.service.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Qualifier("processor")
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
            var message = new MsgBase(
                update.getMessage().getChatId(),
                update.getMessage().getText()
            );

            message.process(this, processor);
        }
    }

    @Override
    public String getBotUsername() {
        return props.name();
    }
}
