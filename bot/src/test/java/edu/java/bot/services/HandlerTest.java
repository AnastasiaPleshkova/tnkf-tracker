package edu.java.bot.services;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.commands.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HandlerTest {

    @MockBean
    private Update update;
    @MockBean
    private Message message;
    @MockBean
    private Chat chat;
    @MockBean
    private UrlService urlService;
    @Autowired
    private Handler handler;

    @BeforeEach
    void init() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1000L);
    }

    @Test
    void correctCommand() {
        String url = "/track https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        Mockito.when(message.text()).thenReturn(url);

        SendMessage msg = handler.handle(update);

        assertThat(msg.getParameters().get("chat_id")).isEqualTo(1000L);
        assertThat(msg.getParameters().get("text")).isEqualTo(Track.MESSAGE_SUCCEEDED);

    }

    @Test
    void incorrectCommand() {
        String url = "/rack https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        Mockito.when(message.text()).thenReturn(url);

        SendMessage msg = handler.handle(update);

        assertThat(msg.getParameters().get("chat_id")).isEqualTo(1000L);
        assertThat(msg.getParameters().get("text")).isEqualTo(Handler.UNKHOWN_KOMMAND);
    }
}
