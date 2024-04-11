package edu.java.scrapper.services.sendUpdates;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.useQueue", havingValue = "true")
@Service
public class KafkaSendUpdate implements SendUpdate {
    @Value("${app.kafka.topicName}")
    private String topicName;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Override
    public void send(LinkUpdateRequest update) {
        try {
            kafkaTemplate.send(topicName, update);
        } catch (Exception e) {
            log.warn("Error during sending message in kafka" + e.getMessage());
        }
    }
}
