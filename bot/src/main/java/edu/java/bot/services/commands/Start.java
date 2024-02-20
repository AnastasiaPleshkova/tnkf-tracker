package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UserService;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class Start implements Command {
    private final UserService userService;

    public static final String NAME = "/start";

    public static final String DESCRIPTION = "Начать общение с ботом";
    public static final String MESSAGE = "Спасибо, что присоединился!";

    public Start(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();
        userService.add(chatId);
        return new SendMessage(chatId, MESSAGE);
    }
}