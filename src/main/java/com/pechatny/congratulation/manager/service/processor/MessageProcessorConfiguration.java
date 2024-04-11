package com.pechatny.congratulation.manager.service.processor;

import com.pechatny.congratulation.manager.db.StateRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageProcessorConfiguration {
    private final StateRepository stateRepository;
    private final MeterRegistry meterRegistry;

    public MessageProcessorConfiguration(StateRepository stateRepository, MeterRegistry meterRegistry) {
        this.stateRepository = stateRepository;
        this.meterRegistry = meterRegistry;
    }

    @Bean
    MessageProcessor messageProcessor() {
        return new MessageProcessorMetric(
                new MessageProcessorLogic(stateRepository),
                meterRegistry
        );
    }
}
