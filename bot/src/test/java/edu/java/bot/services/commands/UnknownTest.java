package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class UnknownTest {
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;
    @InjectMocks
    private Unknown unknown;

    @Test
    void makeMessageTest() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        SendMessage msg = unknown.process(update);
        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Unknown.UNKNOWN_COMMAND)
        );
    }

    @Test
    void getCommandNameTest() {
        assertThat(unknown.getCommandName())
            .isEqualTo(Unknown.UNKNOWN_COMMAND);
    }

    @Test
    void getDescriptionTest() {
        assertThat(unknown.getDescription())
            .isEqualTo(Unknown.UNKNOWN_COMMAND);
    }
}

