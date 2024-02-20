package edu.java.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Slf4j
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    void update() {
        log.info("тут будет проверка ссылок на обновление");
    }

}
