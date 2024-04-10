package edu.java.scrapper.configuration;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.services.sendUpdates.HttpUpdateSendler;
import edu.java.scrapper.services.sendUpdates.KafkaUpdateSendler;
import edu.java.scrapper.services.sendUpdates.UpdateSendler;
import edu.java.scrapper.webClients.BotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
public class UpdateSendlerConfig {
    private final ApplicationConfig applicationConfig;
    private final BotClient botClient;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Bean
    @ConditionalOnProperty(name = "app.useQueue", havingValue = "true")
    public UpdateSendler kafkaUpdateSendler() {
        return new KafkaUpdateSendler(applicationConfig.kafka().topicName(), kafkaTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "app.useQueue", havingValue = "false")
    public UpdateSendler httpUpdateSendler() {
        return new HttpUpdateSendler(botClient);
    }
}
