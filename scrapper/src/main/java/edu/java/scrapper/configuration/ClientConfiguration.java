package edu.java.scrapper.configuration;

import edu.java.scrapper.httpClients.BotHttpClient;
import edu.java.scrapper.httpClients.GitClient;
import edu.java.scrapper.httpClients.GitHubHttpClient;
import edu.java.scrapper.httpClients.StackClient;
import edu.java.scrapper.httpClients.StackOverFlowHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    private final ApplicationConfig applicationConfig;

    @Autowired
    public ClientConfiguration(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public GitClient githubClient() {
        return new GitHubHttpClient(applicationConfig.githubUrl());
    }

    @Bean
    public StackClient stackoverflowClient() {
        return new StackOverFlowHttpClient(applicationConfig.stackoverflowUrl());
    }

    @Bean
    public BotHttpClient botHttpClient() {
        return new BotHttpClient(applicationConfig.botUrl());
    }

}
