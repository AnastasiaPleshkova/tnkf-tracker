package edu.java.bot.config;

import edu.java.bot.MyBot;
import edu.java.bot.services.holder.Holder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBotConfig {
    private final ApplicationConfig applicationConfig;
    private final Holder holder;

    @Bean
    public MyBot telegramBot() {
        MyBot bot = new MyBot(applicationConfig.telegramToken(), holder);
        bot.setBotMenu();
        bot.setUpdatesListener();
        return bot;
    }

}
