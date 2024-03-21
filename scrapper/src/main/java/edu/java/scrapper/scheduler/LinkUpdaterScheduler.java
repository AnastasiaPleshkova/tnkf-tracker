package edu.java.scrapper.scheduler;

import edu.java.scrapper.services.LinkUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;
    private final Integer maxUpdatedRecordsValue;

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}")
    void update() {
        log.info(linkUpdater.update(maxUpdatedRecordsValue) + " ссылок получили обновление");
    }

}
