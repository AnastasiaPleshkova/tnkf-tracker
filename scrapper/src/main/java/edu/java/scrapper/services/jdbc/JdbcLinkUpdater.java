package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.configuration.ApplicationConfig;
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
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private static final String ERROR_MSG = "Произошла ошибка ";
    private static final String COMMITS = "Изменилось количество коммитов у ссылки ";
    private static final String SOME_CHANGES = "Что-то изменилось у ссылки ";
    private static final String ANSWERS = "Изменилось количество ответов у ссылки ";
    private final LinkRepository linkRepository;
    private final GitClient gitClient;
    private final StackClient stackClient;
    private final BotClient botClient;
    @Lazy
    private final ApplicationConfig.Scheduler scheduler;

    @Override
    public int update() {
        int count = 0;
        OffsetDateTime time = OffsetDateTime.now().minus(scheduler.forceCheckDelay());

        List<Link> allLinks = linkRepository.findByLastCheck(time);

        for (Link link : allLinks) {
            try {

                if (!link.getOwnerName().isBlank()) {
                    long commitsCount =
                        gitClient.fetchUserRepoCommits(link.getOwnerName(), link.getRepositoryName()).length;
                    if (link.getCommitsCount() != commitsCount) {
                        linkRepository.updateLinkCommitsCount(link.getId(), commitsCount);
                        sendUpdate(link, COMMITS);
                        count++;
                    }
                    OffsetDateTime lastModifTimeFromLink =
                        gitClient.fetchUserRepo(link.getOwnerName(), link.getRepositoryName()).updatedAt();
                    if (lastModifTimeFromLink.isAfter(link.getLastCheckTime())) {
                        linkRepository.updateLinkCheckTime(link.getId(), lastModifTimeFromLink);
                        sendUpdate(link, SOME_CHANGES);
                        count++;
                    }
                }
                if (!link.getQuestionId().isBlank()) {
                    StackUserResponse.Question answer = stackClient.fetchQuestion(link.getQuestionId()).items().get(0);
                    long answerCount = answer.answerCount();
                    OffsetDateTime lastModifTimeFromLink = answer.lastActivityDate();
                    if (lastModifTimeFromLink.isAfter(link.getLastCheckTime())) {
                        if (answerCount != link.getAnswerCount()) {
                            linkRepository.updateLinkAnswersCount(link.getId(), answerCount);
                            sendUpdate(link, ANSWERS);
                        } else {
                            sendUpdate(link, SOME_CHANGES);
                        }
                        linkRepository.updateLinkCheckTime(link.getId(), lastModifTimeFromLink);
                        count++;
                    }
                }
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
}
