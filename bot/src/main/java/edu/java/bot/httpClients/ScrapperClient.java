package edu.java.bot.httpClients;

import edu.java.bot.dto.request.LinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;

public interface ScrapperClient {
    ListLinksResponse getLinks(String chatId);

    LinkResponse addLinks(String chatId, LinkRequest linkRequest);

    LinkResponse deleteLinks(String chatId, LinkRequest linkRequest);

    void registerChat(String chatId);

    void deleteChat(String chatId);
}
