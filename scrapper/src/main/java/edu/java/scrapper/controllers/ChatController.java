package edu.java.scrapper.controllers;

import edu.java.scrapper.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/tg-chat/{id}")
    public String registerChat(@PathVariable Long id) {
        log.info("Зарегистрировать чат " + id);
        chatService.register(id);
        return "Чат зарегистрирован";
    }

    @DeleteMapping("/api/tg-chat/{id}")
    public String deleteChat(@PathVariable Long id) {
        log.info("Удалить чат " + id);
        chatService.unregister(id);
        return "Чат успешно удалён";
    }
}
