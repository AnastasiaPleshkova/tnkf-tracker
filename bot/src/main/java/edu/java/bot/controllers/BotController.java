package edu.java.bot.controllers;

import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import edu.java.bot.services.SendUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BotController {
    private final SendUpdateService sendUpdateService;

    @PostMapping("/updates")
    public String sendUpdates(@Valid @RequestBody LinkUpdateRequest linkUpdate) {

        sendUpdateService.sendUpdate(linkUpdate);

        return linkUpdate.tgChatIds().length + " пользователям отправлено обновление от ссылки " + linkUpdate.url();
    }
}
