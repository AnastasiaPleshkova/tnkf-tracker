package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.DatabaseUrlService;
import edu.java.bot.utils.CommandRemover;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class Untrack implements Command {
    private final DatabaseUrlService databaseUrlService;

    public static final String NAME = "/untrack";

    public static final String DESCRIPTION = "Удалить ссылку из отслеживания";
    public static final String MESSAGE_SUCCEEDED = "Ссылка успешно удалена";
    public static final String MESSAGE_FAILED = "Произошла ошибка\n";

    @Autowired
    public Untrack(DatabaseUrlService databaseUrlService) {
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
            answer = constructAnswer(chatId, url);
        } catch (Exception e) {
            answer = e.getMessage();
        }
        return new SendMessage(chatId, answer);
    }

    private String constructAnswer(long chatId, String url) {
        try {
            databaseUrlService.remove(chatId, url);
            return MESSAGE_SUCCEEDED;
        } catch (Exception e) {
            return MESSAGE_FAILED + e.getMessage();
        }
    }
}
