package edu.java.bot.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MeterRegistryConfig {

    private final MeterRegistry meterRegistry;

    @Bean
    public Counter processedMessagesCounter() {
        return Counter.builder("processed_messages_count")
            .description("Number of processed messages")
            .register(meterRegistry);
    }
}
