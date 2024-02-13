package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Commandable {
    String getCommandName();

    String getDescription();

    SendMessage makeMessage(Update update);
}
