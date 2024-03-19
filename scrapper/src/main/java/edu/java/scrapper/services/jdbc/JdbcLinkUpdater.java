package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.client.StackUserResponse;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.services.LinkUpdater;
import edu.java.scrapper.webClients.BotClient;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.StackClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JdbcLinkUpdater implements LinkUpdater {
    public static final String GIT = "https://github.com";
    public static final String SOF = "https://stackoverflow.com";
    private static final String ERROR_MSG = "Произошла ошибка ";
    private static final String COMMITS = "Изменилось количество коммитов у ссылки ";
    private static final String SOME_CHANGES = "Что-то изменилось у ссылки ";
    private static final String ANSWERS = "Изменилось количество ответов у ссылки ";
    private final LinkRepository linkRepository;
    private final GitClient gitClient;
    private final StackClient stackClient;
    private final BotClient botClient;

    public JdbcLinkUpdater(
        @Qualifier("jooqLinkRepository") LinkRepository linkRepository,
        GitClient gitClient,
        StackClient stackClient,
        BotClient botClient
    ) {
        this.linkRepository = linkRepository;
        this.gitClient = gitClient;
        this.stackClient = stackClient;
        this.botClient = botClient;
    }

    @Override
    public int update(int updatedRecordsValue) {
        int count = 0;
        OffsetDateTime currentTime = OffsetDateTime.now().withNano(0);
        List<Link> linkToUpdate = linkRepository.findByLastCheckLimit(updatedRecordsValue);

        for (Link link : linkToUpdate) {
            try {
                if (link.getUrl().startsWith(SOF)) {
                    count += updateStackLink(link);
                } else if (link.getUrl().startsWith(GIT)) {
                    count += updateGitLink(link);
                }
                linkRepository.updateLinkCheckTime(link.getId(), currentTime);
            } catch (Exception e) {
                log.info(ERROR_MSG + e.getMessage());
            }
        }
        return count;
    }

    @SneakyThrows
    private void sendUpdate(Link link, String message) {
        LinkUpdateRequest linkUpdate =
            new LinkUpdateRequest(link.getId(), new URI(link.getUrl()), message + link.getUrl(),
                linkRepository.findByUrl(link.getUrl()).stream().map(Chat::getTgChatId).toArray(Long[]::new)
            );
        botClient.sendUpdate(linkUpdate);
    }

    private int updateStackLink(Link link) {
        int count = 0;
        StackUserResponse.Question answer = stackClient.fetchQuestion(link.getQuestionId()).items().get(0);
        long answerCount = answer.answerCount();
        OffsetDateTime lastModifTimeFromLink = answer.lastActivityDate();
        if (lastModifTimeFromLink.isAfter(link.getUpdatedAt())) {
            if (answerCount != link.getAnswerCount()) {
                linkRepository.updateLinkAnswersCount(link.getId(), answerCount);
                sendUpdate(link, ANSWERS);
            } else {
                sendUpdate(link, SOME_CHANGES);
            }
            linkRepository.updateUpdatedAtTime(link.getId(), lastModifTimeFromLink);
            count++;
        }
        return count;

    }

    private int updateGitLink(Link link) {
        int count = 0;
        long commitsCount =
            gitClient.fetchUserRepoCommits(link.getOwnerName(), link.getRepositoryName()).length;
        if (link.getCommitsCount() != commitsCount) {
            linkRepository.updateLinkCommitsCount(link.getId(), commitsCount);
            sendUpdate(link, COMMITS);
            count++;
        }
        OffsetDateTime lastModifTimeFromLink =
            gitClient.fetchUserRepo(link.getOwnerName(), link.getRepositoryName()).updatedAt();
        if (lastModifTimeFromLink.isAfter(link.getUpdatedAt())) {
            linkRepository.updateUpdatedAtTime(link.getId(), lastModifTimeFromLink);
            sendUpdate(link, SOME_CHANGES);
            count++;
        }
        return count;
    }
}
