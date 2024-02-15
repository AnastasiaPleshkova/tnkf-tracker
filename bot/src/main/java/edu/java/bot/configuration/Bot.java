package edu.java.bot.configuration;

import com.pengrad.telegrambot.model.Update;

public interface Bot extends AutoCloseable {

    void setUpdatesListener();

    void execute(Update update);

    void setBotMenu();

    void close();
}
