package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.DatabaseUrlService;
import edu.java.bot.utils.CommandRemover;
import edu.java.bot.utils.UrlValidator;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class Track implements Command {
    private final DatabaseUrlService databaseUrlService;

    public static final String NAME = "/track";

    public static final String DESCRIPTION = "Добавить ссылку для отслеживания";
    public static final String MESSAGE_SUCCEEDED = "Ссылка успешно добавлена!";
    public static final String MESSAGE_FAILED = "Произошла ошибка\n";

    @Autowired
    public Track(DatabaseUrlService databaseUrlService) {
        this.databaseUrlService = databaseUrlService;
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
        String answer;
        try {
            String url = CommandRemover.removeCommand(update.message().text());
            UrlValidator.checkUrl(url);
            databaseUrlService.add(chatId, url);
            answer = MESSAGE_SUCCEEDED;
        } catch (IllegalArgumentException exception) {
            answer = MESSAGE_FAILED + exception.getMessage();
        }
        return new SendMessage(chatId, answer);
    }
}
