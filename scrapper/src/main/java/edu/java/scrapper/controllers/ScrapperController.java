package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.request.LinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ScrapperController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChat(@PathVariable Long id) {
        log.info("Зарегистрировать чат " + id);
        return ResponseEntity.ok("Чат зарегистрирован");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {
        log.info("Удалить чат " + id);
        return ResponseEntity.ok("Чат успешно удалён");
    }

    @GetMapping("/links")
    public ListLinksResponse getAllLinks(@RequestParam(value = "Tg-Chat-Id") Long id) {
        log.info("Получить все отслеживаемые ссылки " + id);
        return new ListLinksResponse(new LinkResponse[0], 0);
    }

    @PostMapping("/links")
    public LinkResponse addLink(
        @RequestParam("Tg-Chat-Id") Long chatId,
        @Valid @RequestBody LinkRequest request
    ) {
        log.info("Добавить отслеживание ссылки");
        return new LinkResponse(chatId, request.link());
    }

    @DeleteMapping("/links")
    public LinkResponse removeLink(
        @RequestParam("Tg-Chat-Id") Long chatId,
        @Valid @RequestBody LinkRequest request
    ) {
        log.info("Убрать отслеживание ссылки");
        return new LinkResponse(chatId, request.link());
    }
}
