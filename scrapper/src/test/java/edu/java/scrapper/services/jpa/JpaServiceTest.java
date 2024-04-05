package edu.java.scrapper.services.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {"app.database-access-type=jpa", "bucket4j.enabled=false", "spring.cache.type=none"})
class JpaServiceTest extends IntegrationTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private JpaChatRepository chatRepository;
    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    void addTest() {
        long chatId1 = 1111;
        long chatId2 = 2222;
        URI uri = new URI("https://github.com/AnastasiaPleshkova/tnkf-tracker");

        chatService.register(chatId1);
        chatService.register(chatId2);

        List<Chat> all = chatRepository.findAll();
        assertAll(
            () -> assertEquals(2, all.size()),
            () -> assertTrue(chatRepository.findByTgChatId(chatId1).isPresent()),
            () -> assertTrue(chatRepository.findByTgChatId(chatId2).isPresent())
        );

        linkService.add(chatId1, uri);
        linkService.add(chatId2, uri);

        Link link = linkRepository.findByUrl(uri.toString()).orElseThrow();
        assertAll(
            () -> assertTrue(linkRepository.findAll().stream().anyMatch(x -> x.getUrl().equals(uri.toString()))),
            () -> assertTrue(linkService.listAllChats(uri).stream().anyMatch(x -> x.getTgChatId().equals(chatId1))),
            () -> assertTrue(linkService.listAllChats(uri).stream().anyMatch(x -> x.getTgChatId().equals(chatId2))),
            () -> assertTrue(linkRepository.existsByChatsIdAndChatsLinksId(all.get(0).getId(), link.getId())),
            () -> assertTrue(chatRepository.findByLinksUrl(uri.toString()).stream()
                .anyMatch(x -> x.getTgChatId().equals(chatId1))),
            () -> assertTrue(linkService.listAll(chatId2).stream().anyMatch(x -> x.getUrl().equals(uri.toString())))
        );
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    void removeTest() {
        long tgChatId1 = 1111;
        chatService.register(tgChatId1);
        URI uri = new URI("https://stackoverflow.com/questions/78161927/why-do-i-see-instead-of-error-output");
        linkService.add(tgChatId1, uri);
        Link link = linkRepository.findByUrl(uri.toString()).orElseThrow();
        Chat chat = chatRepository.findByTgChatId(tgChatId1).orElseThrow();
        assertTrue(linkRepository.existsByChatsIdAndChatsLinksId(chat.getId(), link.getId()));

        linkService.remove(tgChatId1, uri);
        assertFalse(linkRepository.existsByChatsIdAndChatsLinksId(chat.getId(), link.getId()));

        chatService.unregister(tgChatId1);
        assertTrue(chatRepository.findByTgChatId(tgChatId1).isEmpty());
    }

}
