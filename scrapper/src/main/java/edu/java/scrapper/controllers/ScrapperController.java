package edu.java.scrapper.controllers;

import edu.java.scrapper.dto.request.controller.LinkRequest;
import edu.java.scrapper.dto.response.controller.LinkResponse;
import edu.java.scrapper.dto.response.controller.ListLinksResponse;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.util.LinkResponseMapper;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScrapperController {
    private final ChatService chatService;
    private final LinkService linkService;

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

    @GetMapping("/api/links")
    public ListLinksResponse getAllLinks(
        @RequestHeader(value = "Tg-Chat-Id") long id
    ) {

        log.info("Получить все отслеживаемые ссылки " + id);
        Collection<Link> links = linkService.listAll(id);

        return new ListLinksResponse(
            links.stream()
                .map(LinkResponseMapper::mapToLinkResponse)
                .toArray(LinkResponse[]::new),
            links.size()
        );
    }

    @PostMapping("/api/links")
    public LinkResponse addLink(
        @RequestHeader("Tg-Chat-Id") long chatId,
        @Valid @RequestBody LinkRequest request
    ) {
        log.info("Добавить отслеживание ссылки");
        Link link = linkService.add(chatId, request.link());
        return LinkResponseMapper.mapToLinkResponse(link);
    }

    @DeleteMapping("/api/links")
    public LinkResponse removeLink(
        @RequestHeader("Tg-Chat-Id") long chatId,
        @Valid @RequestBody LinkRequest request
    ) {
        log.info("Убрать отслеживание ссылки");
        Link link = linkService.remove(chatId, request.link());
        return LinkResponseMapper.mapToLinkResponse(link);
    }
}
