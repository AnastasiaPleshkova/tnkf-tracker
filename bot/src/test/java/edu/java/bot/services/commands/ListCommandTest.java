package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UrlService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {
    @Mock Update update;
    @Mock Message message;
    @Mock Chat chat;
    @Mock UrlService urlService;
    @InjectMocks
    private ListCommand listCommand;

    @Test
    void shouldReturnMessageForEmptyList() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);
        Mockito.when(urlService.getLinksByUser(100L)).thenReturn(Collections.emptyList());

        SendMessage msg = listCommand.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> Mockito.verify(urlService).getLinksByUser(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(ListCommand.MESSAGE_FOR_EMPTY_LIST)
        );
    }

    @Test
    void shouldReturnTestUrlsList() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);

        List<String> testUrls = List.of(
            "https://github.com/sanyarnd/tinkoff-java-course-2023/",
            "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"
        );
        StringBuilder result = new StringBuilder(ListCommand.MESSAGE);
        testUrls.forEach(x -> result.append("● ").append(x).append(System.lineSeparator()));

        Mockito.when(urlService.getLinksByUser(100L)).thenReturn(testUrls);

        SendMessage msg = listCommand.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> Mockito.verify(urlService).getLinksByUser(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(result.toString())
        );
    }

    @Test
    void getCommandNameTest() {
        assertThat(listCommand.getCommandName())
            .isEqualTo(ListCommand.NAME);
    }

    @Test
    void getDescriptionTest() {
        assertThat(listCommand.getDescription())
            .isEqualTo(ListCommand.DESCRIPTION);
    }
}
