package com.pechatny.congratulation.manager.service.processor;

import com.pechatny.congratulation.manager.command.Commands;
import com.pechatny.congratulation.manager.db.StateRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageProcessorConfiguration {
    private final StateRepository stateRepository;
    private final Commands commandsRepository;
    private final MeterRegistry meterRegistry;

    public MessageProcessorConfiguration(
        StateRepository stateRepository,
        Commands commandsRepository,
        MeterRegistry meterRegistry
    ) {
        this.stateRepository = stateRepository;
        this.commandsRepository = commandsRepository;
        this.meterRegistry = meterRegistry;
    }

    @Bean
    MessageProcessor messageProcessor() {
        return new MessageProcessorMetric(
            new MessageProcessorLogic(stateRepository, commandsRepository),
            meterRegistry
        );
    }
}
