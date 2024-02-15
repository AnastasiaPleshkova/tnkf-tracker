package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UrlService;
import edu.java.bot.utils.UrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackTest {
    @Mock Update update;
    @Mock Message message;
    @Mock Chat chat;
    @Mock UrlService urlService;
    @InjectMocks
    private Track track;

    @Test
    void shouldReturnSuccessMessage() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(100L);
        String receiveMessage = "/track https://github.com/sanyarnd/tinkoff-java-course-2023/";
        when(message.text()).thenReturn(receiveMessage);

        SendMessage msg = track.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Track.MESSAGE_SUCCEEDED)
        );
    }

    @Test
    void shouldReturnErrorMessage() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(100L);
        String receiveMessage = "/track https://google.com/tinkoff-java-course-2023/";
        when(message.text()).thenReturn(receiveMessage);

        SendMessage msg = track.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Track.MESSAGE_FAILED + UrlValidator.MESSAGE)
        );
    }

    @Test
    void getCommandNameTest() {
        assertThat(track.getCommandName())
            .isEqualTo(Track.NAME);
    }

    @Test
    void getDescriptionTest() {
        assertThat(track.getDescription())
            .isEqualTo(Track.DESCRIPTION);
    }
}
