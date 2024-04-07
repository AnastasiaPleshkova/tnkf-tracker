package edu.java.scrapper.services.sendUpdates;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;

public interface UpdateSendler {
    void send(LinkUpdateRequest linkUpdate);
}
