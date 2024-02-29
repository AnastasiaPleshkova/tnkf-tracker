package edu.java.bot.controllers;

import edu.java.bot.dto.request.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BotController {

    @PostMapping("/updates")
    public ResponseEntity<String> sendUpdates(@Valid @RequestBody LinkUpdateRequest linkUpdate) {
        log.info("Получаем обновление по отслеживаемой ссылке");
        return ResponseEntity.ok("Отправлено обновление");
    }
}
