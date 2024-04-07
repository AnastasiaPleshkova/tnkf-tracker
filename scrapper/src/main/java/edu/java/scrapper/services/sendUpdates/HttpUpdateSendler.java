package edu.java.scrapper.services.sendUpdates;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.webClients.BotClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpUpdateSendler implements UpdateSendler {
    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest linkUpdate) {
        botClient.sendUpdate(linkUpdate);
    }
}
