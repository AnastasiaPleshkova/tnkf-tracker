package edu.java.scrapper.configuration;

import edu.java.scrapper.configuration.accessTypes.AccessType;
import edu.java.scrapper.configuration.retry.RetryProperties;
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
    String githubUrl,
    @NotEmpty
    String stackoverflowUrl,
    @NotEmpty
    String botUrl,
    @NotNull
    Integer maxUpdatedRecordsValue,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    @Bean
    RetryProperties retryProperties,
    @NotNull
    @Bean
    Scheduler scheduler,
    @NotNull
    Kafka kafka,
    Boolean useQueue
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record Kafka(@NotNull String bootstrapServers,
                        @NotNull String topicName) {
    }
}
