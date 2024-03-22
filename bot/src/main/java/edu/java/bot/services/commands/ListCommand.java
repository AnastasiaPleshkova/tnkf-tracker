package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UrlService;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final UrlService urlService;

    public static final String NAME = "/list";
    public static final String DESCRIPTION = "Получить список отслеживаемых ссылок";
    public static final String MESSAGE = "*Список отслеживаемых ссылок:*\n";
    public static final String MESSAGE_FOR_EMPTY_LIST = "_Отслеживаемых ссылок пока нет_";

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
        StringBuilder answer = new StringBuilder();

        try {
            List<String> linksByUser = urlService.getLinksByUser(chatId);
            if (linksByUser.isEmpty()) {
                return new SendMessage(chatId, MESSAGE_FOR_EMPTY_LIST).parseMode(ParseMode.Markdown);
            }
            answer.append(MESSAGE);
            linksByUser.forEach(url -> answer.append("● ").append(url).append(System.lineSeparator()));
        } catch (IllegalArgumentException exception) {
            answer.append(exception.getMessage());
        }
        return new SendMessage(chatId, answer.toString()).parseMode(ParseMode.Markdown);
    }
}
