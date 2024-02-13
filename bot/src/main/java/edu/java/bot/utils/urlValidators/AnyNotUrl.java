package edu.java.bot.utils.urlValidators;

import java.net.URL;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class AnyNotUrl implements UrlValidator {
    private UrlValidator next;
    public static final String ERROR_MSG = "Некорректно введен url - ";

    @Override
    public void check(String url, long chatId) {
        if (isInvalidUrl(url)) {
            throw new IllegalArgumentException(ERROR_MSG + url);
        }
        checkNext(url, chatId);
    }

    private boolean isInvalidUrl(String urlString) {
        try {
            new URL(urlString).toURI();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
