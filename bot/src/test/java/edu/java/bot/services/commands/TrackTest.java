package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utils.CommandRemover;
import edu.java.bot.utils.urlValidators.UrlChainValidation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TrackTest {
    @MockBean
    private Update update;
    @MockBean
    private Message message;
    @MockBean
    private Chat chat;
    @MockBean
    private CommandRemover commandRemover;
    @MockBean
    private UrlChainValidation urlChainValidation;
    @Autowired
    private Track track;

    @Test
    void shouldReturnSuccessMessage() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        String receiveMessage = "/track https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Mockito.when(message.text()).thenReturn(receiveMessage);
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Mockito.when(commandRemover.removeCommand(receiveMessage)).thenReturn(url);
        SendMessage msg = track.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> Mockito.verify(commandRemover).removeCommand(receiveMessage),
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Track.MESSAGE_SUCCEEDED)
        );
    }

    @Test
    void shouldReturnErrorMessage() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        String receiveMessage = "track https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Mockito.when(message.text()).thenReturn(receiveMessage);
        Mockito.when(commandRemover.removeCommand(receiveMessage)).thenReturn("");
        doThrow(new IllegalArgumentException("Invalid URL"))
            .when(urlChainValidation).checkUrl("", 100L);
        SendMessage msg = track.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> Mockito.verify(commandRemover).removeCommand(receiveMessage),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(Track.MESSAGE_FAILED + "Invalid URL")
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
