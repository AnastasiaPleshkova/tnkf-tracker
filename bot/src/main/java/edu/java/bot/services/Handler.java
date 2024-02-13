package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.commands.Commandable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Handler implements Handleable {
    public static final String UNKHOWN_KOMMAND = "Kоманда неизвестна";
    private final List<Commandable> commandList;

    @Autowired
    public Handler(List<Commandable> commandList) {
        this.commandList = commandList;
    }

    @Override
    public List<? extends Commandable> getCommands() {
        return commandList;
    }

    public SendMessage handle(Update update) {

        for (Commandable command : commandList) {
            if (update.message().text().startsWith(command.getCommandName())) {
                return command.makeMessage(update);
            }
        }

        return new SendMessage(update.message().chat().id(), UNKHOWN_KOMMAND);
    }
}
