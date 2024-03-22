package edu.java.bot.services;

import edu.java.bot.dto.request.client.LinkRequest;
import edu.java.bot.dto.response.client.ListLinksResponse;
import edu.java.bot.webClients.ScrapperClient;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseUrlService implements UrlService {

    public static final String FIRST_SIGH_IN_MSG = "Для начала работы с ботом необходимо зарегистрироваться /start";
    public static final String NOT_FOUND_URL_MSG = "Не найдена ссылка для удаления среди отслеживаемых вами";
    public static final String ALREADY_TRACKED_URL_MSG = "Ссылка уже среди отслеживаемых вами";
    private final ScrapperClient scrapperClient;

    @Override
    public void add(long chatId, String url) {
        try {
            scrapperClient.addLinks(String.valueOf(chatId), new LinkRequest(new URI(url)));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        log.info(String.format("Пользователь %d добавил ссылку: %s", chatId, url));
    }

    @Override
    public void remove(long chatId, String url) {

        try {
            scrapperClient.deleteLinks(String.valueOf(chatId), new LinkRequest(new URI(url)));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        log.info(String.format("Пользователь %d удалил ссылку : %s", chatId, url));
    }

    @Override
    public List<String> getLinksByUser(long chatId) {
        try {
            ListLinksResponse links = scrapperClient.getLinks(String.valueOf(chatId));
            log.info("Пользователь" + chatId + " запросил список своих ссылок");
            return Arrays.stream(links.links()).map(x -> x.url().toString()).toList();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    @Override
    public void addUser(long chatId) {
        try {
            scrapperClient.registerChat(String.valueOf(chatId));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
