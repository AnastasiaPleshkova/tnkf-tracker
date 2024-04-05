package edu.java.scrapper.configuration;

import edu.java.scrapper.webClients.BotClient;
import edu.java.scrapper.webClients.BotWebClient;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.GitHubWebClient;
import edu.java.scrapper.webClients.StackClient;
import edu.java.scrapper.webClients.StackOverFlowWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final ApplicationConfig applicationConfig;
    private final Retry retry;

    @Bean
    public GitClient githubClient() {
        return new GitHubWebClient(applicationConfig.githubUrl(), retry);
    }

    @Bean
    public StackClient stackoverflowClient() {
        return new StackOverFlowWebClient(applicationConfig.stackoverflowUrl(), retry);
    }

    @Bean
    public BotClient botClient() {
        return new BotWebClient(applicationConfig.botUrl(), retry);
    }

}
