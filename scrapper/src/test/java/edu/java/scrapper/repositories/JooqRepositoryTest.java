package edu.java.scrapper.repositories;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JooqRepositoryTest extends IntegrationTest {
    @Qualifier("jooqChatRepository")
    @Autowired
    private ChatRepository chatRepository;
    @Qualifier("jooqLinkRepository")
    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        long chatId1 = 1111;
        long chatId2 = 2222;
        String admin = "admin";
        OffsetDateTime time = OffsetDateTime.of(2024, 3, 14, 9, 55, 0, 0, ZoneOffset.UTC);
        ChatDto chatDto1 = new ChatDto(chatId1, time, admin);
        ChatDto chatDto2 = new ChatDto(chatId2, time, admin);

        chatRepository.add(chatDto1);
        chatRepository.add(chatDto2);

        List<Chat> result = chatRepository.findAll();

        assertAll(
            () -> assertTrue(result.stream().anyMatch(x -> x.getTgChatId().equals(chatId1))),
            () -> assertTrue(result.stream().anyMatch(x -> x.getTgChatId().equals(chatId2)))
        );

        String url = "https://github.com/AnastasiaPleshkova/tnkf-tracker";
        LinkDto linkDto = new LinkDto(url, "", "", "", (long) 0, time.minusDays(100), (long) 0, time, admin);

        linkRepository.addLink(linkDto);
        Link link = linkRepository.find(url).orElseThrow();

        linkRepository.add(result.get(0).getId(), link.getId());
        linkRepository.add(result.get(1).getId(), link.getId());

        assertAll(
            () -> assertTrue(linkRepository.findByChatId(chatId1).stream()
                .anyMatch(x -> x.getUrl().equals(link.getUrl()))),
            () -> assertTrue(linkRepository.findByChatId(chatId2).stream()
                .anyMatch(x -> x.getUrl().equals(link.getUrl())))
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        long tgChatId1 = 1111;
        long tgChatId2 = 2222;

        Chat chat1 = chatRepository.find(tgChatId1).orElseThrow();
        Chat chat2 = chatRepository.find(tgChatId2).orElseThrow();

        List<Link> list1 = linkRepository.findByChatId(tgChatId1);
        List<Link> list2 = linkRepository.findByChatId(tgChatId2);

        list1.forEach(x -> linkRepository.remove(chat1.getId(), x.getId()));
        list2.forEach(x -> linkRepository.remove(chat2.getId(), x.getId()));

        chatRepository.remove(tgChatId1);
        chatRepository.remove(tgChatId2);

        assertAll(
            () -> assertTrue(linkRepository.findByChatId(chat1.getId()).isEmpty()),
            () -> assertTrue(linkRepository.findByChatId(chat2.getId()).isEmpty()),
            () -> assertTrue(chatRepository.findAll().isEmpty())

        );

    }

}

