package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.services.Handleable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBot implements Bot {
    private final TelegramBot telegramBot;
    private final Handleable holder;

    public MyBot(String token, Handleable holder) {
        this.telegramBot = new TelegramBot(token);
        this.holder = holder;
    }

    public void execute(Update update) {
        try {
            String command = update.message().text().split(" ")[0];
            SendMessage sendMessage = holder.getHandler(command).makeMessage(update);
            telegramBot.execute(sendMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void setUpdatesListener() {
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::execute);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                log.error(String.format("%s %s", e.response().errorCode(), e.response().description()));
            }
        });
    }

    @Override
    public void setBotMenu() {
        BotCommand[] botCommands = holder.getCommands().stream()
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
