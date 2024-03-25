package edu.java.bot.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class CommandRemoverTest {
    @Test
    void correctCommand() {
        String receiveMessage = "/track https://github.com/sanyarnd/tinkoff-java-course-2023/";
        assertThat(CommandRemover.removeCommand(receiveMessage))
            .isEqualTo("https://github.com/sanyarnd/tinkoff-java-course-2023/");
    }

    @Test
    void incorrectCommand() {
        String receiveMessage = "etrack https://github.com/sanyarnd/tinkoff-java-course-2023/";
        Throwable thrown = catchThrowable(() -> CommandRemover.removeCommand(receiveMessage));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(CommandRemover.ERROR_MSG);
    }

}
