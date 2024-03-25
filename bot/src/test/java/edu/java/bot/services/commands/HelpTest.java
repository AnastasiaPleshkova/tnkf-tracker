package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.holder.Holder;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpTest {
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;
    @Mock
    Holder holder;
    @InjectMocks
    Help help;

    @Test
    void makeMessageTestWithCommands(
        @Mock Start start,
        @Mock ListCommand list,
        @Mock Track track,
        @Mock Untrack untrack
    ) {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(100L);

        when(start.getCommandName()).thenReturn(Start.NAME);
        when(start.getDescription()).thenReturn(Start.DESCRIPTION);
        when(list.getCommandName()).thenReturn(ListCommand.NAME);
        when(list.getDescription()).thenReturn(ListCommand.DESCRIPTION);
        when(track.getCommandName()).thenReturn(Track.NAME);
        when(track.getDescription()).thenReturn(Track.DESCRIPTION);
        when(untrack.getCommandName()).thenReturn(Untrack.NAME);
        when(untrack.getDescription()).thenReturn(Untrack.DESCRIPTION);

        Collection<Command> commands = List.of(start, help, list, track, untrack);
        when(holder.getCommands()).thenReturn(commands);

        StringBuilder result = new StringBuilder(Help.MESSAGE);
        commands.forEach(x -> result.append(String.format("%s - %s%n", x.getCommandName(), x.getDescription())));

        SendMessage msg = help.process(update);

        assertThat(msg.getParameters()).containsEntry("chat_id", 100L);
        assertThat(msg.getParameters()).containsEntry("text", result.toString());
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
