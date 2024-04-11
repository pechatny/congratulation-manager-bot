package com.pechatny.congratulation.manager.service.processor;

import com.pechatny.congratulation.manager.bot.Bot;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class MessageProcessorMetric implements MessageProcessor {
    private final MessageProcessor processor;
    private final Counter counter;

    public MessageProcessorMetric(MessageProcessor processor, MeterRegistry meterRegistry) {
        this.counter = Counter
            .builder("congratulation.manager.bot.messages.counter")
            .register(meterRegistry);
        this.processor = processor;
    }

    @Override
    public void processMessage(Bot bot, Long chatId, String message) {
        counter.increment();
        processor.processMessage(bot, chatId, message);
    }
}
