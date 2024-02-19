package edu.java.bot.services;

import edu.java.bot.models.User;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseUserService implements UserService {
    private final DatabaseUrlService databaseUrlService;
    private Map<Long, User> tempUserMap;  // TODO remove after config connection to db

    public DatabaseUserService(DatabaseUrlService databaseUrlService) {
        this.databaseUrlService = databaseUrlService;
    }

    @Override
    public void add(long chatId) {
        if (tempUserMap == null) {
            tempUserMap = new HashMap<>();
        }
        tempUserMap.put(chatId, new User(chatId));
        databaseUrlService.addUser(chatId);
        log.info("Создали нового пользователя с id " + chatId);
    }
}
