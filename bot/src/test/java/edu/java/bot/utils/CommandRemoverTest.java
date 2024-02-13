package edu.java.bot.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
public class CommandRemoverTest {
    @Autowired
    private CommandRemover commandRemover;

    @Test
    void correctCommand() {
        String receiveMessage = "/track https://github.com/sanyarnd/tinkoff-java-course-2023/";
        assertThat(commandRemover.removeCommand(receiveMessage))
            .isEqualTo("https://github.com/sanyarnd/tinkoff-java-course-2023/");
    }

    @Test
    void incorrectCommand() {
        String receiveMessage = "etrack https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Throwable thrown = catchThrowable(() -> commandRemover.removeCommand(receiveMessage));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(CommandRemover.ERROR_MSG);
    }

}
