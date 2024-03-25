package edu.java.scrapper.repositories;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JdbcRepositoryTest extends IntegrationTest {
    @Autowired
    @Qualifier(value = "jdbcChatRepository")
    private ChatRepository chatRepository;
    @Autowired
    @Qualifier(value = "jdbcLinkRepository")
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
            () -> assertEquals(2, result.size()),
            () -> assertTrue(chatRepository.find(chatId1).isPresent()),
            () -> assertTrue(chatRepository.find(chatId2).isPresent())
        );

        String url = "https://github.com/AnastasiaPleshkova/tnkf-tracker";
        LinkDto linkDto = new LinkDto(url, time, (long) 0, (long) 0, time, time, admin);

        linkRepository.addLink(linkDto);
        Link link = linkRepository.find(url).orElseThrow();

        linkRepository.add(result.get(0).getId(), link.getId());
        linkRepository.add(result.get(1).getId(), link.getId());

        assertAll(
            () -> assertTrue(linkRepository.findAll().stream().anyMatch(x -> x.getUrl().equals(url))),
            () -> assertTrue(linkRepository.findChatsByUrl(url).stream()
                .anyMatch(x -> x.getTgChatId().equals(chatId1))),
            () -> assertTrue(linkRepository.findChatsByUrl(url).stream()
                .anyMatch(x -> x.getTgChatId().equals(chatId2))),
            () -> assertTrue(linkRepository.checkTracking(result.get(0).getId(), link.getId()))
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

        assertFalse(linkRepository.checkTracking(chat1.getId(), list1.get(0).getId()));

        chatRepository.remove(tgChatId1);
        chatRepository.remove(tgChatId2);

        assertAll(
            () -> assertTrue(linkRepository.findByChatId(chat1.getId()).isEmpty()),
            () -> assertTrue(linkRepository.findByChatId(chat2.getId()).isEmpty()),
            () -> assertTrue(chatRepository.findAll().isEmpty())

        );

    }

    @Test
    @Transactional
    @Rollback
    void removeUserWithActiveLinksTest() {
        long chatId1 = 1111;
        String admin = "admin";
        OffsetDateTime time = OffsetDateTime.now().withNano(0);
        ChatDto chatDto1 = new ChatDto(chatId1, time, admin);

        chatRepository.add(chatDto1);

        Optional<Chat> createdChat = chatRepository.find(chatId1);
        assertTrue(createdChat.isPresent());

        String url = "https://github.com/AnastasiaPleshkova/library";
        LinkDto linkDto = new LinkDto(url, time, (long) 0, (long) 0, time, time, admin);

        linkRepository.addLink(linkDto);
        Link link = linkRepository.find(url).orElseThrow();

        linkRepository.add(createdChat.get().getId(), link.getId());

        assertAll(
            () -> assertTrue(linkRepository.findByChatId(chatId1).stream()
                .anyMatch(x -> x.getUrl().equals(link.getUrl()))),
            () -> assertDoesNotThrow(() -> chatRepository.remove(chatId1)),
            () -> assertTrue(chatRepository.find(chatId1).isEmpty())
        );
    }

    @Test
    @Transactional
    @Rollback
    void updateTest() {
        String admin = "admin";
        OffsetDateTime todayTime = OffsetDateTime.now().withNano(0);
        OffsetDateTime oldTime = todayTime.minusDays(1000);
        String url = "https://github.com/AnastasiaPleshkova/CheckFuel";
        LinkDto linkDto = new LinkDto(url, oldTime, (long) 0, (long) 0, oldTime, oldTime, admin);

        linkRepository.addLink(linkDto);

        List<Link> byLastCheckLimit = linkRepository.findByLastCheckLimit(1);
        Link linkToUpdate = byLastCheckLimit.get(0);

        linkToUpdate.setLastCheckTime(todayTime);
        linkToUpdate.setUpdatedAt(todayTime);
        linkToUpdate.setCommitsCount((long) 2);
        linkToUpdate.setAnswersCount((long) 2);
        linkRepository.update(linkToUpdate);

        Link resultLink = linkRepository.find(url).get();

        assertAll(
            () -> assertEquals(linkToUpdate.getUrl(), url),
            () -> assertTrue(todayTime.isEqual(resultLink.getLastCheckTime())),
            () -> assertTrue(todayTime.isEqual(resultLink.getUpdatedAt())),
            () -> assertEquals(2, (long) resultLink.getCommitsCount()),
            () -> assertEquals(2, (long) resultLink.getAnswersCount())
        );
    }
}


