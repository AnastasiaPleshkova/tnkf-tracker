package edu.java.bot.services;

import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {
    private final SendUpdateService sendUpdateService;
    private final BotProducer botDlqProducer;

    @KafkaListener(topics = "${app.kafka.topicName}", groupId = "${app.kafka.consumer.group-id}")
    public void listen(LinkUpdateRequest update) {
        try {
            log.info("Обновление из кафки: " + update);
            sendUpdateService.sendUpdate(update);
        } catch (Exception e) {
            log.warn("Ошибка, отправлено в dlq: " + update);
            botDlqProducer.sendUpdate(update);
        }
    }

}
