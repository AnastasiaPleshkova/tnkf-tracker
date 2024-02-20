package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.DatabaseUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class StartTest {
    @Mock
    DatabaseUserService databaseUserService;
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;
    @InjectMocks
    private Start start;

    @Test
    void makeMessageTest() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        SendMessage msg = start.process(update);
        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> Mockito.verify(databaseUserService).add(100L),
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
