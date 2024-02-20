package edu.java.bot.config;

import edu.java.bot.MyBot;
import edu.java.bot.services.holder.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBotConfig {
    private final ApplicationConfig applicationConfig;
    private final Holder holder;

    @Autowired
    public MyBotConfig(ApplicationConfig applicationConfig, Holder holder) {
        this.applicationConfig = applicationConfig;
        this.holder = holder;
    }

    @Bean
    public MyBot telegramBot() {
        MyBot bot = new MyBot(applicationConfig.telegramToken(), holder);
        bot.setBotMenu();
        bot.setUpdatesListener();
        return bot;
    }

}
