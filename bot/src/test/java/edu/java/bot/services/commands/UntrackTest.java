package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.DatabaseUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UntrackTest {
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;
    @Mock
    DatabaseUrlService databaseUrlService;
    @InjectMocks
    private Untrack untrack;

    @Test
    void shouldReturnSuccessMessage() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        String receiveMessage = "/untrack https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Mockito.when(message.text()).thenReturn(receiveMessage);

        SendMessage msg = untrack.process(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Untrack.MESSAGE_SUCCEEDED)
        );
    }

    @Test
    void shouldReturnErrorMessage() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        String receiveMessage = "/untrack https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Mockito.when(message.text()).thenReturn(receiveMessage);

        doThrow(new IllegalArgumentException(DatabaseUrlService.NOT_FOUND_URL_MSG))
            .when(databaseUrlService).remove(100L, "https://github.com/sanyarnd/tinkoff-java-course-2023/");

        SendMessage msg = untrack.process(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(
                Untrack.MESSAGE_FAILED + DatabaseUrlService.NOT_FOUND_URL_MSG)
        );
    }

    @Test
    void getCommandNameTest() {
        assertThat(untrack.getCommandName())
            .isEqualTo(Untrack.NAME);
    }

    @Test
    void getDescriptionTest() {
        assertThat(untrack.getDescription())
            .isEqualTo(Untrack.DESCRIPTION);
    }
}
