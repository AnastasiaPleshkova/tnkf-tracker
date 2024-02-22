package edu.java.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "app.scheduler.enabled", matchIfMissing = true)
@Slf4j
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}")
    void update() {
        log.info("тут будет проверка ссылок на обновление");
    }

}
