package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdate);
}
