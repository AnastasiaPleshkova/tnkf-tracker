package edu.java.scrapper.configuration;

import edu.java.scrapper.scheduler.LinkUpdaterScheduler;
import edu.java.scrapper.services.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final LinkUpdater linkUpdater;
    private final ApplicationConfig applicationConfig;

    @Bean
    @ConditionalOnProperty(name = "app.scheduler.enabled", matchIfMissing = true)
    public LinkUpdaterScheduler linkUpdaterScheduler() {
        return new LinkUpdaterScheduler(linkUpdater, applicationConfig.maxUpdatedRecordsValue());
    }
}
