package edu.java.bot.config;

import edu.java.bot.httpClients.ScrapperHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsConfig {
    private final ApplicationConfig applicationConfig;

    @Autowired
    public ClientsConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public ScrapperHttpClient githubClient() {
        return new ScrapperHttpClient(applicationConfig.scrapperUrl());
    }

}


