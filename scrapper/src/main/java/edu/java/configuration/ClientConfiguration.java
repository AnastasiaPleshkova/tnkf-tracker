package edu.java.configuration;

import edu.java.httpClients.GitClient;
import edu.java.httpClients.GitHubHttpClient;
import edu.java.httpClients.StackClient;
import edu.java.httpClients.StackOverFlowHttpClient;
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

}
