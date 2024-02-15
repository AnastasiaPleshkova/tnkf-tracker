package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class Unknown implements Commandable {
    public static final String UNKHOWN_KOMMAND = "Kоманда неизвестна";

    @Override
    public String getCommandName() {
        return UNKHOWN_KOMMAND;
    }

    @Override
    public String getDescription() {
        return UNKHOWN_KOMMAND;
    }

    @Override
    public SendMessage makeMessage(Update update) {
        return new SendMessage(update.message().chat().id(), UNKHOWN_KOMMAND);
    }
}
