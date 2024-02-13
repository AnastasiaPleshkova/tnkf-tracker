package edu.java.bot.services;

import edu.java.bot.models.User;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UrlService urlService;
    private Map<Long, User> tempUserMap;  // TODO remove after config connection to db

    public UserService(UrlService urlService) {
        this.urlService = urlService;
    }

    public void add(long chatId) {
        if (tempUserMap == null) {
            tempUserMap = new HashMap<>();
        }
        tempUserMap.put(chatId, new User(chatId));
        urlService.addUser(chatId);
        log.info("Created new user with id " + chatId);
    }
}
