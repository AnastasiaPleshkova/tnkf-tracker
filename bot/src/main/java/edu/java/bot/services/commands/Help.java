package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.holder.Holder;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class Help implements Command {
    private final Holder holder;

    public static final String NAME = "/help";

    public static final String DESCRIPTION = "Получить справку о работе бота";
    public static final String MESSAGE = """
        Помогу чем смогу ;В
        На данный момент я поддерживаю несколько ресурсов, а именно GitHub и StackOverflow.\s
        И могу отрабатывать следующие команды:
        """;

    public Help(@Lazy Holder holder) {
        this.holder = holder;
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
        Long id = update.message().chat().id();
        return new SendMessage(id, getAllCommandsDesc());
    }

    private String getAllCommandsDesc() {
        StringBuilder sb = new StringBuilder(MESSAGE);

        holder.getCommands().forEach(command -> sb.append(String.format(
            "%s - %s%n",
            command.getCommandName(),
            command.getDescription()
        )));

        return sb.toString();
    }
}
