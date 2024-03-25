package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.client.GitCommitsResponse;
import edu.java.scrapper.dto.response.client.GitUserResponse;
import edu.java.scrapper.dto.response.client.StackQuestion;
import edu.java.scrapper.dto.response.client.StackUserResponse;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
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
import org.springframework.beans.factory.annotation.Qualifier;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcLinkUpdaterTest {
    @Mock
    @Qualifier(value = "jdbcLinkRepository")
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
        Link link = new Link(
            id,
            "https://stackoverflow.com/questions/123/test-url",
            time,
            (long) 0,
            (long) 0,
            time,
            time,
            "test",
            new HashSet<>()
        );

        OffsetDateTime now = OffsetDateTime.now();
        StackQuestion questionItem = new StackQuestion("123", now, 0);

        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(questionItem)));

        jdbcLinkUpdater.updateStackLink(link);

        assertEquals(now, link.getUpdatedAt());
        verify(botClient, times(1)).sendUpdate(any(LinkUpdateRequest.class));
    }

    @Test
    void testUpdateGitLink() {
        OffsetDateTime time = OffsetDateTime.now().minusDays(1);
        long id = 1;

        Link link = new Link(id,
            "https://github.com/AnastasiaPleshkova/tnkf-tracker",
            time,
            (long)0,(long)0,
            time,
            time,
            "test",
            new HashSet<>()
        );

        OffsetDateTime now = OffsetDateTime.now();
        when(gitClient.fetchUserRepo("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitUserResponse("name", now));
        when(gitClient.fetchUserRepoCommits("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitCommitsResponse[0]);

        jdbcLinkUpdater.updateGitLink(link);

        assertEquals(now, link.getUpdatedAt());
        verify(botClient, times(1)).sendUpdate(any(LinkUpdateRequest.class));
    }

    @Test
    void testAnyUpdate() {
        int maxUpdatedRecordsValue = 4;
        OffsetDateTime today = OffsetDateTime.now().withNano(0);
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
        when(gitClient.fetchUserRepoCommits("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitCommitsResponse[0]);
        when(gitClient.fetchUserRepo("AnotherRepoName", "test"))
            .thenReturn(new GitUserResponse("test", yesterday));
        when(gitClient.fetchUserRepoCommits("AnotherRepoName", "test"))
            .thenReturn(new GitCommitsResponse[0]);
        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion("123", today, 0))));
        when(stackClient.fetchQuestion("123456"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion(
                "123456",
                yesterday, 0
            ))));

        when(linkRepository.findByLastCheckLimit(maxUpdatedRecordsValue)).thenReturn(linksToUpdate);

        int updatedCount = jdbcLinkUpdater.update(maxUpdatedRecordsValue);

        assertAll(
            () -> assertEquals(2, updatedCount),
            () -> assertEquals(today, linksToUpdate.get(0).getUpdatedAt()),
            () -> assertEquals(today, linksToUpdate.get(0).getLastCheckTime()),
            () -> assertEquals(yesterday, linksToUpdate.get(1).getUpdatedAt()),
            () -> assertEquals(today, linksToUpdate.get(1).getLastCheckTime()),
            () -> assertEquals(today, linksToUpdate.get(2).getUpdatedAt()),
            () -> assertEquals(today, linksToUpdate.get(2).getLastCheckTime()),
            () -> assertEquals(yesterday, linksToUpdate.get(3).getUpdatedAt()),
            () -> assertEquals(today, linksToUpdate.get(3).getLastCheckTime()),
            () -> verify(botClient, times(2)).sendUpdate(any(LinkUpdateRequest.class))
        );

    }

    @Test
    void testCommitsUpdate() {
        int maxUpdatedRecordsValue = 2;
        OffsetDateTime today = OffsetDateTime.now();
        OffsetDateTime yesterday = today.minusDays(1);
        long id = 1;
        String url1 = "https://github.com/AnastasiaPleshkova/tnkf-tracker";
        String url2 = "https://github.com/AnotherRepoName/test";
        List<Link> linksToUpdate = List.of(
            new Link(id, url1, yesterday, (long) 0,
                (long) 0, yesterday, yesterday, "test"
            ),
            new Link(id, url2, yesterday, (long) 0,
                (long) 0, yesterday, yesterday, "test"
            )
        );

        when(gitClient.fetchUserRepo("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitUserResponse("tnkf-tracker", today));
        when(gitClient.fetchUserRepoCommits("AnastasiaPleshkova", "tnkf-tracker"))
            .thenReturn(new GitCommitsResponse[0]);

        when(gitClient.fetchUserRepo("AnotherRepoName", "test"))
            .thenReturn(new GitUserResponse("test", yesterday));
        GitCommitsResponse[] gitCommitsResponse =
            new GitCommitsResponse[] {
                new GitCommitsResponse(new GitCommitsResponse.Commit("first commit"), "some url")};
        when(gitClient.fetchUserRepoCommits("AnotherRepoName", "test"))
            .thenReturn(gitCommitsResponse);

        when(linkRepository.findByLastCheckLimit(maxUpdatedRecordsValue)).thenReturn(linksToUpdate);

        int updatedCount = jdbcLinkUpdater.update(maxUpdatedRecordsValue);

        String someChanges = "Что-то изменилось у ссылки ";
        String commits = "Изменилось количество коммитов у ссылки ";

        assertAll(
            () -> assertEquals(2, updatedCount),
            () -> assertEquals(today, linksToUpdate.get(0).getUpdatedAt()),
            () -> assertEquals(0, linksToUpdate.get(0).getCommitsCount()),
            () -> assertEquals(yesterday, linksToUpdate.get(1).getUpdatedAt()),
            () -> assertEquals(1, linksToUpdate.get(1).getCommitsCount()),
            () -> verify(botClient, times(1)).sendUpdate(argThat(arg ->
                arg.description().contains(commits) && arg.url().toString().equals(url2))),
            () -> verify(botClient, times(1)).sendUpdate(argThat(arg ->
                arg.description().startsWith(someChanges) && arg.url().toString().equals(url1)))
        );

    }

    @Test
    void testAnswersUpdate() {
        int maxUpdatedRecordsValue = 3;
        OffsetDateTime today = OffsetDateTime.now();
        OffsetDateTime yesterday = today.minusDays(1);
        long id = 1;
        String url1 = "https://stackoverflow.com/questions/123/test-url";
        String url2 = "https://stackoverflow.com/questions/123456/another-test-url";
        String url3 = "https://stackoverflow.com/questions/9999/no-change-test-url";
        List<Link> linksToUpdate = List.of(
            new Link(id, url1, yesterday, (long) 0,
                (long) 0, yesterday, yesterday, "test"
            ),
            new Link(id, url2, yesterday, (long) 0,
                (long) 0, yesterday, yesterday, "test"
            ),
            new Link(id, url3, yesterday, (long) 0,
                (long) 0, yesterday, yesterday, "test"
            )
        );

        when(stackClient.fetchQuestion("123"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion("123", today, 0))));
        when(stackClient.fetchQuestion("123456"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion(
                "123456", today, 5))));
        when(stackClient.fetchQuestion("9999"))
            .thenReturn(new StackUserResponse(Collections.singletonList(new StackQuestion("123", yesterday, 0))));

        when(linkRepository.findByLastCheckLimit(maxUpdatedRecordsValue)).thenReturn(linksToUpdate);

        int updatedCount = jdbcLinkUpdater.update(maxUpdatedRecordsValue);

        String someChanges = "Что-то изменилось у ссылки ";
        String answers = "Изменилось количество ответов у ссылки ";

        assertAll(
            () -> assertEquals(2, updatedCount),
            () -> assertEquals(today, linksToUpdate.get(0).getUpdatedAt()),
            () -> assertEquals(0, linksToUpdate.get(0).getAnswersCount()),
            () -> assertEquals(today, linksToUpdate.get(1).getUpdatedAt()),
            () -> assertEquals(5, linksToUpdate.get(1).getAnswersCount()),
            () -> assertEquals(yesterday, linksToUpdate.get(2).getUpdatedAt()),
            () -> assertEquals(0, linksToUpdate.get(2).getAnswersCount()),
            () -> verify(botClient, times(1)).sendUpdate(argThat(arg ->
                arg.description().contains(answers) && arg.url().toString().equals(url2))),
            () -> verify(botClient, times(1)).sendUpdate(argThat(arg ->
                arg.description().startsWith(someChanges) && arg.url().toString().equals(url1)))
        );

    }

}
