package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpTest {
    @MockBean
    private Update update;
    @MockBean
    private Message message;
    @MockBean
    private Chat chat;
    @Autowired
    private Help help;

    @Test
    void makeMessageTest() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(100L);

        SendMessage msg = help.makeMessage(update);

        assertThat(msg.getParameters().get("chat_id")).isEqualTo(100L);
        assertThat(msg.getParameters().get("text")).isEqualTo(Help.MESSAGE);
    }

    @Test
    void getCommandNameTest() {
        assertThat(help.getCommandName())
            .isEqualTo(Help.NAME);
    }

    @Test
    void getDescriptionTest() {
        assertThat(help.getDescription())
            .isEqualTo(Help.DESCRIPTION);
    }
}
