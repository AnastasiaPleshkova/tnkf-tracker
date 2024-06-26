package edu.java.bot.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotEmpty
    String scrapperUrl,
    @NotNull
    @Bean
    RetryProperties retryProperties,

    @NotNull
    Kafka kafka
) {
    public record RetryProperties(RetryType type, int[] statuses, int attempts, Duration delay) {
        public enum RetryType {
            CONSTANT, LINEAR, EXPONENTIAL
        }

    }

    public record Kafka(@NotNull String bootstrapServers,
                        @NotNull String topicName,
                        @NotNull Consumer consumer,
                        @NotNull String errorTopicName) {

        public record Consumer(@NotNull String groupId) {
        }
    }
}
