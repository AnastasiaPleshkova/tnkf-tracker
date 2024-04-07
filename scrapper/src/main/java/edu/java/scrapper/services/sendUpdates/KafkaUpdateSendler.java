package edu.java.scrapper.services.sendUpdates;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class KafkaUpdateSendler implements UpdateSendler {
    private final ApplicationConfig applicationConfig;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Override
    public void send(LinkUpdateRequest update) {
        try {
            kafkaTemplate.send(applicationConfig.kafka().topicName(), update);
        } catch (Exception e) {
            log.warn("Error during sending message in kafka" + e.getMessage());
        }
    }
}
