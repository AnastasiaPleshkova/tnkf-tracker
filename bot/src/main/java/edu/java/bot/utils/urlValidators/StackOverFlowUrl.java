package edu.java.bot.utils.urlValidators;

import edu.java.bot.services.UrlService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class StackOverFlowUrl implements UrlValidator {
    private final UrlService urlService;
    private UrlValidator next;

    public StackOverFlowUrl(UrlService urlService) {
        this.urlService = urlService;
    }

    @Override
    public void check(String url, long chatId) {
        if (url.startsWith("https://stackoverflow.com")) {
            urlService.add(chatId, url);
            return;
        }
        checkNext(url, chatId);
    }
}
