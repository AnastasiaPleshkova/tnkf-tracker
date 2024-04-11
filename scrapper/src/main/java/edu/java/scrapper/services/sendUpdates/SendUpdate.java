package edu.java.scrapper.services.sendUpdates;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;

public interface SendUpdate {
    void send(LinkUpdateRequest linkUpdate);
}
