package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command extends Comparable<Command> {
    String getCommandName();

    String getDescription();

    SendMessage process(Update update);

    @Override
    default int compareTo(Command other) {
        return this.getCommandName().compareTo(other.getCommandName());
    }

}
