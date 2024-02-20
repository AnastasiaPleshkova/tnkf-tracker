package edu.java.bot.services.holder;

import edu.java.bot.services.commands.Command;
import edu.java.bot.services.commands.Help;
import edu.java.bot.services.commands.ListCommand;
import edu.java.bot.services.commands.Start;
import edu.java.bot.services.commands.Track;
import edu.java.bot.services.commands.Unknown;
import edu.java.bot.services.commands.Untrack;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommandHolderTest {

    @Autowired
    CommandHolder commandHolder;

    @Test
    void correctTrackCommand() {
        String url = "/track";
        Command command = commandHolder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Track.class);
    }

    @Test
    void correctUntrackCommand() {
        String url = "/untrack";
        Command command = commandHolder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Untrack.class);
    }

    @Test
    void correctListCommand() {
        String url = "/list";
        Command command = commandHolder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(ListCommand.class);
    }

    @Test
    void correctHelpCommand() {
        String url = "/help";
        Command command = commandHolder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Help.class);
    }

    @Test
    void correctStartCommand() {
        String url = "/start";
        Command command = commandHolder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Start.class);
    }

    @Test
    void incorrectCommand() {
        String url = "/rack";
        Command command = commandHolder.getHandler(url);
        assertThat(command.getClass()).isEqualTo(Unknown.class);
    }

    @Test
    void correctCommandList() {
        Collection<Command> commands = commandHolder.getCommands();

        assertAll(
            "commands",
            () -> assertNotNull(commands),
            () -> assertEquals(5, commands.size()),
            () -> assertTrue(commands.stream().anyMatch(cmd -> cmd.getCommandName().equals(Start.NAME))),
            () -> assertTrue(commands.stream().anyMatch(cmd -> cmd.getCommandName().equals(Help.NAME))),
            () -> assertTrue(commands.stream().anyMatch(cmd -> cmd.getCommandName().equals(ListCommand.NAME))),
            () -> assertTrue(commands.stream().anyMatch(cmd -> cmd.getCommandName().equals(Track.NAME))),
            () -> assertTrue(commands.stream().anyMatch(cmd -> cmd.getCommandName().equals(Untrack.NAME)))
        );
    }

}
