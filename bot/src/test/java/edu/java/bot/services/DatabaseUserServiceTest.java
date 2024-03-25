package edu.java.bot.services;

import edu.java.bot.webClients.ScrapperClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class DatabaseUserServiceTest {
    @InjectMocks
    DatabaseUserService databaseUserService;

    @Mock
    ScrapperClient scrapperClient;

    @Test
    void addUser() {
        long chatId = 999;
        assertDoesNotThrow(() -> databaseUserService.add(chatId));
    }
}
