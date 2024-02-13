package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.commands.Commandable;
import java.util.List;

public interface Handleable {
    List<? extends Commandable> getCommands();

    SendMessage handle(Update update);

}
