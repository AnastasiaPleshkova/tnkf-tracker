package edu.java.bot.services;

import java.util.List;

public interface UrlService {
    void add(long chatId, String url);

    void remove(long chatId, String url);

    List<String> getLinksByUser(long chatId);

    void addUser(long chatId);
}
