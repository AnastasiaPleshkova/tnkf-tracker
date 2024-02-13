package edu.java.bot.utils.urlValidators;

import edu.java.bot.services.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
public class StackOverFlowUrlTest {

    @MockBean
    private UrlService urlService;
    @Autowired
    private StackOverFlowUrl stackOverFlowUrlValidator;

    @Test
    void validUrl() {
        String url = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        assertThatCode(() -> stackOverFlowUrlValidator.check(url, 100L))
            .doesNotThrowAnyException();
    }

    @Test
    void invalidUrl() {
        String invalidUrl = "https://overflow.com/search?q=unsupported%20link";
        Throwable thrown = catchThrowable(() -> stackOverFlowUrlValidator.check(invalidUrl, 100L));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(UrlValidator.MESSAGE);
    }

}
