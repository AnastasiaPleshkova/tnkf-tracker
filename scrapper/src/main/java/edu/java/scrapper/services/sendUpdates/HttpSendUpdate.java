package edu.java.scrapper.services.sendUpdates;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.webClients.BotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.useQueue", havingValue = "false")
@Service
public class HttpSendUpdate implements SendUpdate {
    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest linkUpdate) {
        botClient.sendUpdate(linkUpdate);
    }
}
