package edu.java.bot.services;

import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImplSendErrorService implements SendErrorService {
    @Value("${app.kafka.error-topic-name}")
    private String errorTopicName;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Override
    public void sendErrorUpdate(LinkUpdateRequest linkUpdate) {
        kafkaTemplate.send(errorTopicName, linkUpdate);
    }

}
