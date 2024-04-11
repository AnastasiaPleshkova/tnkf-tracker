package edu.java.bot.services;

import edu.java.bot.dto.request.controller.LinkUpdateRequest;

public interface BotProducer {
    void sendUpdate(LinkUpdateRequest linkUpdate);
}
