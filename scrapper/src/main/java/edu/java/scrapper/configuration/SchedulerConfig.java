package edu.java.scrapper.configuration;

import edu.java.scrapper.scheduler.LinkUpdaterScheduler;
import edu.java.scrapper.services.LinkUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private LinkUpdater linkUpdater;


    @Bean
    @ConditionalOnProperty(name = "app.scheduler.enabled", matchIfMissing = true)
    public LinkUpdaterScheduler linkUpdaterScheduler() {
        return new LinkUpdaterScheduler(linkUpdater);
    }
}
