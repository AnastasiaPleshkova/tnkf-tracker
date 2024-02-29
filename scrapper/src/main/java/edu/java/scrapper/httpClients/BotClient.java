package edu.java.scrapper.httpClients;

import edu.java.scrapper.dto.request.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdate);
}
