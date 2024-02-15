package edu.java.bot.services;

import edu.java.bot.models.User;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock UrlService urlService;
    @InjectMocks UserService userService;

    @Test
    void addUser() throws NoSuchFieldException, IllegalAccessException {
        long chatId = 123456789;
        userService.add(chatId);

        Field field = UserService.class.getDeclaredField("tempUserMap");
        field.setAccessible(true);
        Map<Long, User> map = (Map<Long, User>) field.get(userService);

        verify(urlService).addUser(chatId);
        assertThat(map.get(chatId)).isEqualTo(new User(chatId));
    }
}
