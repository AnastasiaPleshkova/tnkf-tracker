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
public class GitHubUrlTest {

    @MockBean
    private UrlService urlService;

    @Autowired
    private GitHubUrl gitHubUrlValidator;

    @Test
    void validUrl() {
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";
        assertThatCode(() -> gitHubUrlValidator.check(url, 100L))
            .doesNotThrowAnyException();
    }

    @Test
    void invalidUrl() {
        String invalidUrl = "https://giithub.com/sanyarnd/tinkoff-java-course-2023/";
        Throwable thrown = catchThrowable(() -> gitHubUrlValidator.check(invalidUrl, 100L));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(UrlValidator.MESSAGE);
    }

}
