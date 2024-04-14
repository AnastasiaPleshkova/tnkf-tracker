package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.services.holder.Holder;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBot {
    private final TelegramBot telegramBot;
    private final Holder holder;
    private final Counter processedMessagesCounter;

    public MyBot(String token, Holder holder, Counter processedMessagesCounter) {
        this.telegramBot = new TelegramBot(token);
        this.holder = holder;
        this.processedMessagesCounter = processedMessagesCounter;
    }

    public void execute(Update update) {
        executeSafely(() -> {
            String command = update.message().text().split(" ")[0];
            SendMessage sendMessage = holder.getHandler(command).process(update);
            executeMessage(sendMessage);
        }, "Произошла ошибка во время исполнения команды");
    }

    public void executeMessage(SendMessage sendMessage) {
        executeSafely(() -> {
            telegramBot.execute(sendMessage);
            processedMessagesCounter.increment();
        }, "Произошла ошибка во время отправки сообщения");
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

    public void setBotMenu() {
        executeSafely(() -> {
            BotCommand[] botCommands = holder.getCommands().stream()
                .map(x -> new BotCommand(x.getCommandName(), x.getDescription())).toArray(BotCommand[]::new);
            telegramBot.execute(new SetMyCommands(botCommands));
        }, "Произошла ошибка во время установки меню команд бота");
    }

    public void close() {
        telegramBot.removeGetUpdatesListener();
        telegramBot.shutdown();
    }

    private void executeSafely(Runnable task, String errorMessage) {
        try {
            task.run();
        } catch (Exception e) {
            log.error(errorMessage, e);
        }
    }

}
