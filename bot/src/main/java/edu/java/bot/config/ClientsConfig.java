package edu.java.bot.config;

import edu.java.bot.webClients.ScrapperClient;
import edu.java.bot.webClients.ScrapperWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import reactor.util.retry.Retry;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ClientsConfig {
    private final ApplicationConfig applicationConfig;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperWebClient(applicationConfig.scrapperUrl(), retry());
    }


    public Retry retry() {
        return switch (applicationConfig.retryProperties().type()) {
            case CONSTANT -> Retry.max(applicationConfig.retryProperties().attempts())
                .filter(this::isRetryableStatusCode);
            case LINEAR -> Retry.fixedDelay(applicationConfig.retryProperties().attempts(),
                    applicationConfig.retryProperties().delay())
                .filter(this::isRetryableStatusCode);
            case EXPONENTIAL -> Retry.backoff(applicationConfig.retryProperties().attempts(),
                    applicationConfig.retryProperties().delay())
                .filter(this::isRetryableStatusCode);
        };
    }

    private boolean isRetryableStatusCode(Throwable th) {
        if (th instanceof HttpClientErrorException ex) {
            return Arrays.stream(applicationConfig.retryProperties().statuses())
                .anyMatch(code -> code == ex.getStatusCode().value());
        }
        return false;
    }
}


