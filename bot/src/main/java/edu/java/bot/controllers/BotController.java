package edu.java.bot.controllers;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.MyBot;
import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import jakarta.validation.Valid;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BotController {
    private final MyBot myBot;

    @PostMapping("/updates")
    public String sendUpdates(@Valid @RequestBody LinkUpdateRequest linkUpdate) {
        String message = "Тут обновление по отслеживаемой ссылке " + linkUpdate.url();
        log.info(message);

        Arrays.stream(linkUpdate.tgChatIds())
            .map(id -> new SendMessage(id, message))
            .forEach(x -> myBot.getTelegramBot().execute(x));

        return linkUpdate.tgChatIds().length + " пользователям отправлено обновление от ссылки " + linkUpdate.url();
    }
}
