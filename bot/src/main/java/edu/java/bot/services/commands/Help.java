package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class Help implements Commandable {
    private final List<? extends Commandable> commandList;

    public static final String NAME = "/help";

    public static final String DESCRIPTION = "Получить справку о работе бота";
    public static final String MESSAGE = """
        Помогу чем смогу ;В
        На данный момент я поддерживаю несколько ресурсов, а именно GitHub и StackOverflow.\s
        И могу отрабатывать следующие команды:
        """;

    public Help(List<Commandable> commandList) {
        this.commandList = commandList;
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
        Long id = update.message().chat().id();
        return new SendMessage(id, getAllCommandsDesc());
    }

    private String getAllCommandsDesc() {
        StringBuilder sb = new StringBuilder(MESSAGE);
        String s = "%s - %s%n";
        commandList.forEach(command -> sb.append(String.format(
            s,
            command.getCommandName(),
            command.getDescription()
        )));
        sb.append(String.format(s, this.getCommandName(), this.getDescription()));

        return sb.toString();
    }
}
