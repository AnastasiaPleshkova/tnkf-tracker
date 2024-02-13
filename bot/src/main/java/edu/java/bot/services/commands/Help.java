package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class Help implements Commandable {

    public static final String NAME = "/help";

    public static final String DESCRIPTION = "Получить справку о работе бота";
    public static final String MESSAGE = "Помогу чем смогу ;В\n"
        + "На данный момент я поддерживаю несколько ресурсов, а именно GitHub и StackOverflow. "
        + "И могу отрабатывать следующие команды:\n"
        + "/start - начать со мной работать\n"
        + "/help - получить помощь по командам\n"
        + "/list - получить список отслеживаемых ссылок\n"
        + "/track - добавить ссылку к отслеживанию\n"
        + "/untrack - удалить ссылку из отслеживания\n";

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
        return new SendMessage(update.message().chat().id(), MESSAGE);
    }
}
