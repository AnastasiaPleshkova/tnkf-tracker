package edu.java.bot.config;

import edu.java.bot.webClients.ScrapperClient;
import edu.java.bot.webClients.ScrapperWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientsConfig {
    private final ApplicationConfig applicationConfig;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperWebClient(applicationConfig.scrapperUrl());
    }

}


