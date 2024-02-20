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
public class DatabaseUrlService implements UrlService {

    public static final String FIRST_SIGH_IN_MSG = "Для начала работы с ботом необходимо зарегистрироваться /start";
    public static final String NOT_FOUND_URL_MSG = "Не найдена ссылка для удаления среди отслеживаемых вами";
    public static final String ALREADY_TRACKED_URL_MSG = "Ссылка уже среди отслеживаемых вами";
    private Map<Long, List<String>> tempMap = new HashMap<>(); //TODO remove after config connection to db

    @Override
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
        log.info(String.format("Пользователь %d добавил ссылку: %s", chatId, url));
    }

    @Override
    public void remove(long chatId, String url) {
        List<String> list = new ArrayList<>(getLinksByUser(chatId));
        if (list.contains(url)) {
            list.remove(url);
            tempMap.put(chatId, list);
        } else {
            throw new IllegalArgumentException(NOT_FOUND_URL_MSG);
        }
        log.info(String.format("Пользователь %d удалил ссылку : %s", chatId, url));
    }

    @Override
    public List<String> getLinksByUser(long chatId) {
        if (tempMap.containsKey(chatId)) {
            log.info("Пользователь" + chatId + " запросил список своих ссылок");
            return List.copyOf(tempMap.get(chatId));
        } else {
            throw new IllegalArgumentException(FIRST_SIGH_IN_MSG);
        }
    }

    @Override
    public void addUser(long chatId) {
        if (!tempMap.containsKey(chatId)) {
            tempMap.put(chatId, Collections.emptyList());
        }
    }

}
