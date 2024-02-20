package edu.java.configuration;

import edu.java.httpClients.GitClient;
import edu.java.httpClients.GitHubHttpClient;
import edu.java.httpClients.StackClient;
import edu.java.httpClients.StackOverFlowHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitClient githubClient() {
        return new GitHubHttpClient();
    }

    @Bean
    public StackClient stackoverflowClient() {
        return new StackOverFlowHttpClient();
    }

}
