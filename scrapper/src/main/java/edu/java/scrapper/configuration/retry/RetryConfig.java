package edu.java.scrapper.configuration.retry;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
public class RetryConfig {

    private final RetryProperties retryProperties;

    @Bean
    public Retry retry() {

        return switch (retryProperties.type()) {
            case CONSTANT -> Retry.max(retryProperties.attempts())
                .filter(this::isRetryableStatusCode);
            case LINEAR -> Retry.fixedDelay(retryProperties.attempts(), retryProperties.delay())
                .filter(this::isRetryableStatusCode);
            case EXPONENTIAL -> Retry.backoff(retryProperties.attempts(), retryProperties.delay())
                .filter(this::isRetryableStatusCode);
        };


    }

    private boolean isRetryableStatusCode(Throwable th) {
        if (th instanceof HttpClientErrorException ex) {
            return Arrays.stream(retryProperties.statuses()).anyMatch(code -> code == ex.getStatusCode().value());
        }
        return false;
    }
}
