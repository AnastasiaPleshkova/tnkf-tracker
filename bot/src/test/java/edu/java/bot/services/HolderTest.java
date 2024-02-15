package edu.java.bot.services;

import edu.java.bot.services.commands.Commandable;
import edu.java.bot.services.commands.Help;
import edu.java.bot.services.commands.ListCommand;
import edu.java.bot.services.commands.Start;
import edu.java.bot.services.commands.Track;
import edu.java.bot.services.commands.Unknown;
import edu.java.bot.services.commands.Untrack;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HolderTest {

    @Mock Start start;
    @Mock Help help;
    @Mock ListCommand list;
    @Mock Track track;
    @Mock Untrack untrack;
    Holder holder;
    List<Commandable> commandList;

    @BeforeEach
    void init() {
        when(start.getCommandName()).thenReturn(Start.NAME);
        when(help.getCommandName()).thenReturn(Help.NAME);
        when(list.getCommandName()).thenReturn(ListCommand.NAME);
        when(track.getCommandName()).thenReturn(Track.NAME);
        when(untrack.getCommandName()).thenReturn(Untrack.NAME);
        commandList = List.of(start, help, list, track, untrack);
        holder = new Holder(commandList);
    }

    @Test
    void correctTrackCommand() {
        String url = "/track";
        Commandable command = holder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Track.class);
    }

    @Test
    void correctUntrackCommand() {
        String url = "/untrack";
        Commandable command = holder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Untrack.class);
    }

    @Test
    void correctListCommand() {
        String url = "/list";
        Commandable command = holder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(ListCommand.class);
    }

    @Test
    void correctHelpCommand() {
        String url = "/help";
        Commandable command = holder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Help.class);
    }

    @Test
    void correctStartCommand() {
        String url = "/start";
        Commandable command = holder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Start.class);
    }

    @Test
    void incorrectCommand() {
        String url = "/rack";
        Commandable command = holder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Unknown.class);
    }

}
