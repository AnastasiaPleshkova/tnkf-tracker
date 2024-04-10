package edu.java.bot.services;

import edu.java.bot.dto.request.controller.LinkUpdateRequest;

public interface SendErrorService {

    void sendErrorUpdate(LinkUpdateRequest linkUpdate);
}
