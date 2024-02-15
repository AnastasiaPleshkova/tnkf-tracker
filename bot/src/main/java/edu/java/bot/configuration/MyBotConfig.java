package edu.java.bot.configuration;

import edu.java.bot.services.Handleable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBotConfig {
    private final ApplicationConfig applicationConfig;
    private final Handleable holder;

    @Autowired
    public MyBotConfig(ApplicationConfig applicationConfig, Handleable holder) {
        this.applicationConfig = applicationConfig;
        this.holder = holder;
    }

    @Bean
    public Bot getTelegramBot() {
        Bot bot = new MyBot(applicationConfig.telegramToken(), holder);
        bot.setBotMenu();
        bot.setUpdatesListener();
        return bot;
    }

}
