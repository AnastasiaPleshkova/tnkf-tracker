package edu.java.bot.services;

import edu.java.bot.dto.request.controller.LinkUpdateRequest;

public interface SendUpdateService {
    void sendUpdate(LinkUpdateRequest linkUpdate);

}
