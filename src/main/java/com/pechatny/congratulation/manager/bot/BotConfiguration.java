package com.pechatny.congratulation.manager.bot;

import com.pechatny.congratulation.manager.service.MessageProcessor;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfiguration {
    private final BotProperties props;
    private final MessageProcessor processor;

    public BotConfiguration(BotProperties props, MessageProcessor processor) {
        this.props = props;
        this.processor = processor;
    }

    @PostConstruct
    public void registerBot() throws BotConfigurationException {
        try {
            var bot = new Bot(props, processor);
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new BotConfigurationException(e);
        }
    }
}
