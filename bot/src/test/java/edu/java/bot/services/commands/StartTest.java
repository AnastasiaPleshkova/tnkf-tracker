package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class StartTest {
    @MockBean
    private UserService userService;
    @MockBean
    private Update update;
    @MockBean
    private Message message;
    @MockBean
    private Chat chat;
    @Autowired
    private Start start;

    @Test
    void makeMessageTest() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        SendMessage msg = start.makeMessage(update);
        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> Mockito.verify(userService).add(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Start.MESSAGE)
        );
    }

    @Test
    void getCommandNameTest() {
        assertThat(start.getCommandName())
            .isEqualTo(Start.NAME);
    }

    @Test
    void getDescriptionTest() {
        assertThat(start.getDescription())
            .isEqualTo(Start.DESCRIPTION);
    }
}
