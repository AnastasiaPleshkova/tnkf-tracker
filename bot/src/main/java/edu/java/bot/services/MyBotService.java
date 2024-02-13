package edu.java.bot.services;

import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.configuration.Bot;
import edu.java.bot.configuration.MyBot;
import org.springframework.stereotype.Service;

@Service
public class MyBotService {
    private final Bot bot;
    private final Handleable handler;

    public MyBotService(MyBot bot, Handler handler) {
        this.bot = bot;
        this.handler = handler;
        bot.setBotMenu(handler.getCommands());
        startUpdatesListener();
    }

    private void startUpdatesListener() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> bot.execute(handler.handle(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

}
