package edu.java.bot.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UrlValidatorTest {

    @Test
    void testValidUrlGit() {
        String validUrl = "https://github.com/AnastasiaPleshkova/tnkf-tracker/pull/1";
        assertDoesNotThrow(() -> UrlValidator.checkUrl(validUrl));
    }

    @Test
    void testValidUrlSOF() {
        String validUrl = "https://stackoverflow.com/questions/33310960/java-enumerating-list-in-mockitos-thenreturn";
        assertDoesNotThrow(() -> UrlValidator.checkUrl(validUrl));
    }

    @Test
    void incorrectUrl() {
        String invalidUrl = "https://google.com";
        Throwable thrown = catchThrowable(() -> CommandRemover.removeCommand(invalidUrl));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(CommandRemover.ERROR_MSG);
    }

}
