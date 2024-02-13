package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UrlService;
import edu.java.bot.utils.CommandRemover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Untrack implements Commandable {
    private final UrlService urlService;
    private final CommandRemover commandRemover;

    public static final String NAME = "/untrack";

    public static final String DESCRIPTION = "Удалить ссылку из отслеживания";
    public static final String MESSAGE_SUCCEEDED = "Ссылка успешно удалена";
    public static final String MESSAGE_FAILED = "Произошла ошибка\n";

    @Autowired
    public Untrack(UrlService urlService, CommandRemover commandRemover) {
        this.urlService = urlService;
        this.commandRemover = commandRemover;
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
    public SendMessage makeMessage(Update update) {
        long chatId = update.message().chat().id();
        String answer;
        try {
            String url = commandRemover.removeCommand(update.message().text());
            answer = constructAnswer(chatId, url);
        } catch (Exception e) {
            answer = e.getMessage();
        }
        return new SendMessage(chatId, answer);
    }

    private String constructAnswer(long chatId, String url) {
        try {
            urlService.remove(chatId, url);
            return MESSAGE_SUCCEEDED;
        } catch (Exception e) {
            return MESSAGE_FAILED + e.getMessage();
        }
    }
}
