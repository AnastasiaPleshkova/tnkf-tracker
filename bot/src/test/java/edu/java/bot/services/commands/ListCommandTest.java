package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UrlService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class ListCommandTest {
    @MockBean
    private Update update;
    @MockBean
    private Message message;
    @MockBean
    private Chat chat;
    @MockBean
    private UrlService urlService;
    @Autowired
    private ListCommand listCommand;

    @BeforeEach
    void init() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
    }

    @Test
    void shouldReturnMessageForEmptyList() {
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
    void shouldReturnList() {
        Mockito.when(chat.id()).thenReturn(100L);
        Mockito.when(urlService.getLinksByUser(100L)).thenReturn(
            List.of(
                "https://github.com/sanyarnd/tinkoff-java-course-2023/",
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"
            )
        );

        SendMessage msg = listCommand.makeMessage(update);

        assertAll(
            "Message parameters",
            () -> assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L),
            () -> Mockito.verify(urlService).getLinksByUser(100L),
            () -> assertThat(msg.getParameters().get("text")).isEqualTo(
                "*Список отслеживаемых ссылок:*\n" +
                    "● https://github.com/sanyarnd/tinkoff-java-course-2023/\n" +
                    "● https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c\n"
            )
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
