package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

    @Autowired
    private ApplicationConfig applicationConfig;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    public TelegramBot getTelegramBot() {
        return new TelegramBot(applicationConfig.telegramToken());
    }

}
