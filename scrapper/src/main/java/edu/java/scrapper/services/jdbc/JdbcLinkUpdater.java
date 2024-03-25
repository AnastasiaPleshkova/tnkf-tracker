package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.client.StackQuestion;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private static final String GIT = "https://github.com";
    private static final String SOF = "https://stackoverflow.com";
    private static final String ERROR_MSG = "Ссылка недоступна id: %d, url:%s";
    private static final String SOME_CHANGES = "Что-то изменилось у ссылки ";
    private static final String COMMITS = "Изменилось количество коммитов у ссылки ";
    private static final String ANSWERS = "Изменилось количество ответов у ссылки ";
    private final LinkRepository linkRepository;
    private final GitClient gitClient;
    private final StackClient stackClient;
    private final BotClient botClient;
    private int count;

    @Override
    @SneakyThrows
    public int update(int maxUpdatedRecordsValue) {
        count = 0;
        OffsetDateTime currentTime = OffsetDateTime.now().withNano(0);
        List<Link> linkToUpdate = linkRepository.findByLastCheckLimit(maxUpdatedRecordsValue);

        for (Link link : linkToUpdate) {
            try {
                if (link.getUrl().startsWith(SOF)) {
                    updateStackLink(link);
                } else if (link.getUrl().startsWith(GIT)) {
                    updateGitLink(link);
                }
                link.setLastCheckTime(currentTime);
                linkRepository.update(link);
            } catch (Exception e) {
                log.info(ERROR_MSG + e.getMessage());
            }
        }
        return count;
    }

    void updateStackLink(Link link) {
        Pattern pattern = Pattern.compile("https://stackoverflow.com/questions/(\\d+)/.*");
        Matcher matcher = pattern.matcher(link.getUrl());
        if (matcher.find()) {
            StackQuestion question = stackClient.fetchQuestion(matcher.group(1)).items().get(0);
            OffsetDateTime lastModifTimeFromLink = question.lastActivityDate();
            long currentAnswersCount = question.answerCount();
            if (lastModifTimeFromLink.isAfter(link.getUpdatedAt())) {
                if (currentAnswersCount != link.getAnswersCount()) {
                    link.setAnswersCount(currentAnswersCount);
                    sendUpdate(link, ANSWERS);
                } else {
                    sendUpdate(link, SOME_CHANGES);
                }
                link.setUpdatedAt(lastModifTimeFromLink);
                count++;
            }
        }
    }

    void updateGitLink(Link link) {
        Pattern pattern = Pattern.compile("https://github.com/([^/]+)/([^/]+)");
        Matcher matcher = pattern.matcher(link.getUrl());
        if (matcher.find()) {
            long currentCommitsCount =
                gitClient.fetchUserRepoCommits(matcher.group(1), matcher.group(2)).length;
            if (link.getCommitsCount() != currentCommitsCount) {
                link.setCommitsCount(currentCommitsCount);
                sendUpdate(link, COMMITS);
                count++;
            }

            OffsetDateTime lastModifTimeFromLink =
                gitClient.fetchUserRepo(matcher.group(1), matcher.group(2)).updatedAt();
            if (lastModifTimeFromLink.isAfter(link.getUpdatedAt())) {
                link.setUpdatedAt(lastModifTimeFromLink);
                sendUpdate(link, SOME_CHANGES);
                count++;
            }

        }
    }

    @SneakyThrows
    private void sendUpdate(Link link, String message) {
        LinkUpdateRequest linkUpdate =
            new LinkUpdateRequest(link.getId(), new URI(link.getUrl()), message + link.getUrl(),
                linkRepository.findChatsByUrl(link.getUrl()).stream().map(Chat::getTgChatId).toArray(Long[]::new)
            );
        botClient.sendUpdate(linkUpdate);
    }
}

