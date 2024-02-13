package edu.java.bot.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlService {

    public static final String FIRST_SIGH_IN_MSG = "Для начала работы с ботом необходимо зарегистрироваться /start";
    public static final String NOT_FOUND_URL_MSG = "Не найдена ссылка для удаления среди отслеживаемых вами";
    public static final String ALREADY_TRACKED_URL_MSG = "Ссылка уже среди отслеживаемых вами";
    private Map<Long, List<String>> tempMap = new HashMap<>(); //TODO remove after config connection to db

    public void add(long chatId, String url) {

        if (tempMap.containsKey(chatId)) {
            List<String> list = new ArrayList<>(tempMap.get(chatId));
            if (!list.contains(url)) {
                list.add(url);
            } else {
                throw new IllegalArgumentException(ALREADY_TRACKED_URL_MSG);
            }
            tempMap.put(chatId, list);
        } else {
            throw new IllegalArgumentException(FIRST_SIGH_IN_MSG);
        }
        log.info(String.format("User %d added link: %s", chatId, url));
    }

    public void remove(long chatId, String url) {
        List<String> list = getLinksByUser(chatId);
        if (list.contains(url)) {
            list.remove(url);
        } else {
            throw new IllegalArgumentException(NOT_FOUND_URL_MSG);
        }
        log.info(String.format("User %d removed link: %s", chatId, url));
    }

    public List<String> getLinksByUser(long chatId) {
        if (tempMap.containsKey(chatId)) {
            log.info("Get urls list of user " + chatId);
            return tempMap.get(chatId);
        } else {
            throw new IllegalArgumentException(FIRST_SIGH_IN_MSG);
        }
    }

    void addUser(long chatId) {
        if (!tempMap.containsKey(chatId)) {
            tempMap.put(chatId, Collections.emptyList());
        }
    }

}
