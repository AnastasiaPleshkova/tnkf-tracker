package edu.java.bot.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotEmpty
    String scrapperUrl,
    @NotNull
    @Bean
    RetryProperties retryProperties
) {
    public record RetryProperties(RetryType type, int[] statuses, int attempts, Duration delay) {
        public enum RetryType {
            CONSTANT, LINEAR, EXPONENTIAL
        }

    }
}
