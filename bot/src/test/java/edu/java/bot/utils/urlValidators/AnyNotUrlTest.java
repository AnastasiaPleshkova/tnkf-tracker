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
public class AnyNotUrlTest {

    @MockBean
    private UrlService urlService;
    @Autowired
    private AnyNotUrl anyNotUrlValidator;

    @Test
    void validUrl() {
        assertThatCode(() -> anyNotUrlValidator.check("https://github.com/sanyarnd/tinkoff-java-course-2023/", 100L))
            .doesNotThrowAnyException();
    }

    @Test
    void invalidUrl() {
        String invalidUrl = "invalid_url";
        Throwable thrown = catchThrowable(() -> anyNotUrlValidator.check(invalidUrl, 100L));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(AnyNotUrl.ERROR_MSG + invalidUrl);
    }

    @Test
    void nullUrl() {
        Throwable thrown = catchThrowable(() -> anyNotUrlValidator.check(null, 100L));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(AnyNotUrl.ERROR_MSG + "null");
    }

    @Test
    void emptyUrl() {
        Throwable thrown = catchThrowable(() -> anyNotUrlValidator.check("", 100L));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(AnyNotUrl.ERROR_MSG + "");
    }

}
