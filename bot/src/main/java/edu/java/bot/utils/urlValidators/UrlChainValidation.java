package edu.java.bot.utils.urlValidators;

import org.springframework.stereotype.Component;

@Component
public class UrlChainValidation {
    private final AnyNotUrl anyNotUrl;

    public UrlChainValidation(AnyNotUrl anyNotUrl, GitHubUrl gitHubUrl, StackOverFlowUrl stackOverFlowUrl) {
        this.anyNotUrl = anyNotUrl;
        anyNotUrl.setNext(gitHubUrl);
        gitHubUrl.setNext(stackOverFlowUrl);
    }

    public void checkUrl(String url, long chatId) {
        anyNotUrl.check(url, chatId);
    }
}
