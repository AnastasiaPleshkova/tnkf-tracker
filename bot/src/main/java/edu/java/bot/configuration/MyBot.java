package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.services.commands.Commandable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyBot implements Bot {
    private final TelegramBot telegramBot;

    public MyBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        try {
            telegramBot.execute(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void setUpdatesListener(UpdatesListener listener) {
        telegramBot.setUpdatesListener(listener, e -> {
            if (e.response() != null) {
                log.error(e.response().errorCode() + " " + e.response().description());
            }
        });
    }

    @Override
    public void setBotMenu(List<? extends Commandable> commandList) {
        BotCommand[] botCommands = commandList.stream()
            .map(x -> new BotCommand(x.getCommandName(), x.getDescription())).toArray(BotCommand[]::new);
        try {
            telegramBot.execute(new SetMyCommands(botCommands));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
        telegramBot.shutdown();
    }
}
