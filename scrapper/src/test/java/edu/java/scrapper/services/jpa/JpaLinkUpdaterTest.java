package edu.java.scrapper.services.jpa;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.client.GitUserResponse;
import edu.java.scrapper.dto.response.client.StackQuestion;
import edu.java.scrapper.dto.response.client.StackUserResponse;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.webClients.BotClient;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.StackClient;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaLinkUpdaterTest {
    @Mock
    private JpaLinkRepository linkRepository;

    @Mock
    private JpaChatRepository chatRepository;

    @Mock
    private GitClient gitClient;

    @Mock
    private StackClient stackClient;

    @Mock
    private BotClient botClient;

    @InjectMocks
    private JpaLinkUpdater jpaLinkUpdater;

    @Test
    void testUpdateStackLink() {
        OffsetDateTime time = OffsetDateTime.now().minusDays(1);
        String url = "https://stackoverflow.com/questions/123/test-url";
        long id = 1;
        Link link = new Link(id, url, time, (long)0,(long)0,time, time, "test", new HashSet<>());

        StackQuestion questionItem = new StackQuestion("123", OffsetDateTime.now(), 0);

        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(questionItem)));
        when(chatRepository.findByLinksUrl(url)).thenReturn(Collections.singletonList(new Chat(1L,
            1L,
            time,
            "test",
            new HashSet<>())));

        jpaLinkUpdater.updateStackLink(link);

        verify(botClient, times(1)).sendUpdate(any(LinkUpdateRequest.class));
    }

    @Test
    void testUpdateGitLink() {
        OffsetDateTime time = OffsetDateTime.now().minusDays(1);
        long id = 1;
        String url = "https://github.com/AnastasiaPleshkova/tnkf-tracker";
        Link link = new Link(id, url, time, (long)0,(long)0,time, time, "test", new HashSet<>());

        when(gitClient.fetchUserRepo("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitUserResponse("name", OffsetDateTime.now()));

        jpaLinkUpdater.updateGitLink(link);

        verify(botClient, times(1)).sendUpdate(any(LinkUpdateRequest.class));
    }

    @Test
    void testUpdate() {
        int maxUpdatedRecordsValue = 4;
        OffsetDateTime today = OffsetDateTime.now();
        OffsetDateTime yesterday = today.minusDays(1);
        long id = 1;
        List<Link> linksToUpdate = List.of(
            new Link(
                id,
                "https://github.com/AnastasiaPleshkova/tnkf-tracker",
                yesterday,
                (long)0,(long)0,
                yesterday,
                yesterday,
                "test",
                new HashSet<>()
            ),
            new Link(
                id,
                "https://github.com/AnotherRepoName/test",
                yesterday,
                (long)0,(long)0,
                yesterday,
                yesterday,
                "test",
                new HashSet<>()
            ),
            new Link(
                id,
                "https://stackoverflow.com/questions/123/test-url",
                yesterday,
                (long)0,(long)0,
                yesterday,
                yesterday,
                "test",
                new HashSet<>()
            ),
            new Link(
                id,
                "https://stackoverflow.com/questions/123456/test-url",
                yesterday,
                (long)0,(long)0,
                yesterday,
                yesterday,
                "test",
                new HashSet<>()
            )
        );

        when(gitClient.fetchUserRepo("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitUserResponse("tnkf-tracker", today));
        when(gitClient.fetchUserRepo("AnotherRepoName", "test"))
            .thenReturn(new GitUserResponse("test", yesterday));
        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion("123", today, 0))));
        when(stackClient.fetchQuestion("123456"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion(
                "123456",
                yesterday, 0
            ))));

        when(linkRepository.findAllByOrderByLastCheckTimeAsc(Pageable.ofSize(maxUpdatedRecordsValue))).thenReturn(
            linksToUpdate);

        int updatedCount = jpaLinkUpdater.update(maxUpdatedRecordsValue);

        assertAll(
            () -> assertEquals(2, updatedCount),
            () -> verify(botClient, times(2)).sendUpdate(any(LinkUpdateRequest.class))
        );

    }

}

