package edu.java.bot.services;

import edu.java.bot.webClients.ScrapperClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final ScrapperClient scrapperClient;

    @Override
    public void add(long chatId) {
        try {
            scrapperClient.registerChat(String.valueOf(chatId));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        log.info("Создали нового пользователя с id " + chatId);
    }
}
