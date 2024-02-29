package edu.java.scrapper.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}")
    void update() {
        log.info("тут будет проверка ссылок на обновление");
    }

}