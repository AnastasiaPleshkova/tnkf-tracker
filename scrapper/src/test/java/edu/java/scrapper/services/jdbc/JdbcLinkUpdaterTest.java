package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.client.GitUserResponse;
import edu.java.scrapper.dto.response.client.StackUserResponse;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.webClients.BotClient;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.StackClient;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcLinkUpdaterTest {
    @Mock
    private LinkRepository linkRepository;

    @Mock
    private GitClient gitClient;

    @Mock
    private StackClient stackClient;

    @Mock
    private BotClient botClient;

    @InjectMocks
    private JdbcLinkUpdater jdbcLinkUpdater;

    @Test
    void testUpdateStackLink() {
        OffsetDateTime time = OffsetDateTime.now().minusDays(1);
        long id = 1;
        Link link = new Link(id, "https://stackoverflow.com/questions/123/test-url", time, time, time, "test");

        StackUserResponse.Question questionItem = new StackUserResponse.Question("123", OffsetDateTime.now());

        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(questionItem)));

        jdbcLinkUpdater.updateStackLink(link);

        verify(linkRepository, times(1)).updateUpdatedAtTime(eq(1L), any(OffsetDateTime.class));
        verify(botClient, times(1)).sendUpdate(any(LinkUpdateRequest.class));
    }

    @Test
    void testUpdateGitLink() {
        OffsetDateTime time = OffsetDateTime.now().minusDays(1);
        long id = 1;
        Link link = new Link(id, "https://github.com/AnastasiaPleshkova/tnkf-tracker", time, time, time, "test");

        when(gitClient.fetchUserRepo("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitUserResponse("name", OffsetDateTime.now()));

        jdbcLinkUpdater.updateGitLink(link);

        verify(linkRepository, times(1)).updateUpdatedAtTime(eq(1L), any(OffsetDateTime.class));
        verify(botClient, times(1)).sendUpdate(any(LinkUpdateRequest.class));
    }

    @Test
    void testUpdate() {
        int maxUpdatedRecordsValue = 4;
        OffsetDateTime today = OffsetDateTime.now();
        OffsetDateTime yesterday = today.minusDays(1);
        long id = 1;
        List<Link> linksToUpdate = List.of(
            new Link(id, "https://github.com/AnastasiaPleshkova/tnkf-tracker", yesterday, yesterday, yesterday, "test"),
            new Link(id, "https://github.com/AnotherRepoName/test", yesterday, yesterday, yesterday, "test"),
            new Link(id, "https://stackoverflow.com/questions/123/test-url", yesterday, yesterday, yesterday, "test"),
            new Link(id, "https://stackoverflow.com/questions/123456/test-url", yesterday, yesterday, yesterday, "test")
        );

        when(gitClient.fetchUserRepo("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitUserResponse("tnkf-tracker", today));
        when(gitClient.fetchUserRepo("AnotherRepoName", "test"))
            .thenReturn(new GitUserResponse("test", yesterday));
        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackUserResponse.Question("123", today))));
        when(stackClient.fetchQuestion("123456"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackUserResponse.Question(
                "123456",
                yesterday
            ))));

        when(linkRepository.findByLastCheckLimit(maxUpdatedRecordsValue)).thenReturn(linksToUpdate);

        int updatedCount = jdbcLinkUpdater.update(maxUpdatedRecordsValue);

        assertAll(
            () -> assertEquals(2, updatedCount),
            () -> verify(linkRepository, times(2)).updateUpdatedAtTime(eq(id), any(OffsetDateTime.class)),
            () -> verify(linkRepository, times(4)).updateLinkCheckTime(eq(id), any(OffsetDateTime.class)),
            () -> verify(botClient, times(2)).sendUpdate(any(LinkUpdateRequest.class))
        );

    }

}
