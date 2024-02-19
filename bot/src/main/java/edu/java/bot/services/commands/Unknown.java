package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class Unknown implements Command {
    public static final String UNKNOWN_COMMAND = "Kоманда неизвестна";

    @Override
    public String getCommandName() {
        return UNKNOWN_COMMAND;
    }

    @Override
    public String getDescription() {
        return UNKNOWN_COMMAND;
    }

    @Override
    public SendMessage process(Update update) {
        return new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND);
    }
}
