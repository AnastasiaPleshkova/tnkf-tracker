package edu.java.bot.webClients;

import edu.java.bot.dto.request.client.LinkRequest;
import edu.java.bot.dto.response.client.LinkResponse;
import edu.java.bot.dto.response.client.ListLinksResponse;

public interface ScrapperClient {
    ListLinksResponse getLinks(String chatId);

    LinkResponse addLinks(String chatId, LinkRequest linkRequest);

    LinkResponse deleteLinks(String chatId, LinkRequest linkRequest);

    void registerChat(String chatId);

    void deleteChat(String chatId);
}
