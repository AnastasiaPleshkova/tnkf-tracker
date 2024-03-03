package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.request.controller.LinkRequest;
import edu.java.scrapper.dto.response.controller.LinkResponse;
import edu.java.scrapper.dto.response.controller.ListLinksResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ScrapperController {

    @PostMapping("/api/tg-chat/{id}")
    public String registerChat(@PathVariable Long id) {
        log.info("Зарегистрировать чат " + id);
        return "Чат зарегистрирован";
    }

    @DeleteMapping("/api/tg-chat/{id}")
    public String deleteChat(@PathVariable Long id) {
        log.info("Удалить чат " + id);
        return "Чат успешно удалён";
    }

    @GetMapping("/api/links")
    public ListLinksResponse getAllLinks(@RequestParam(value = "Tg-Chat-Id") Long id) {
        log.info("Получить все отслеживаемые ссылки " + id);
        return new ListLinksResponse(new LinkResponse[0], 0);
    }

    @PostMapping("/api/links")
    public LinkResponse addLink(
        @RequestParam("Tg-Chat-Id") Long chatId,
        @Valid @RequestBody LinkRequest request
    ) {
        log.info("Добавить отслеживание ссылки");
        return new LinkResponse(chatId, request.link());
    }

    @DeleteMapping("/api/links")
    public LinkResponse removeLink(
        @RequestParam("Tg-Chat-Id") Long chatId,
        @Valid @RequestBody LinkRequest request
    ) {
        log.info("Убрать отслеживание ссылки");
        return new LinkResponse(chatId, request.link());
    }
}
