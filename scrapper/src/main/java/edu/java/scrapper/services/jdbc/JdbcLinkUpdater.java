package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
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
    private static final String ERROR_MSG = "Ссылка недоступна id: %d, url:%s";
    private final LinkRepository linkRepository;
    private final GitClient gitClient;
    private final StackClient stackClient;
    private final BotClient botClient;

    @Override
    @SneakyThrows
    public int update() {
        int count = 0;
        OffsetDateTime time = OffsetDateTime.now().withMinute(0);

        List<Link> allLinks = linkRepository.findByLastCheck(time);

        for (Link link : allLinks) {
            OffsetDateTime lastModifTimeFromLink = null;
            if (link.getUrl().startsWith("https://github.com")) {
                Pattern pattern = Pattern.compile("https://github.com/([^/]+)/([^/]+)");
                Matcher matcher = pattern.matcher(link.getUrl());
                if (matcher.find()) {
                    try {
                        lastModifTimeFromLink = gitClient.fetchUserRepo(matcher.group(1), matcher.group(2)).updatedAt();
                    } catch (Exception e) {
                        log.info(String.format(ERROR_MSG, link.getId(), link.getUrl()));
                    }

                }
            } else if (link.getUrl().startsWith("https://stackoverflow.com")) {
                Pattern pattern = Pattern.compile("https://stackoverflow.com/questions/(\\d+)/.*");
                Matcher matcher = pattern.matcher(link.getUrl());
                if (matcher.find()) {
                    try {
                        lastModifTimeFromLink =
                            stackClient.fetchQuestion(matcher.group(1)).items().get(0).lastActivityDate();
                    } catch (Exception e) {
                        log.info(String.format(ERROR_MSG, link.getId(), link.getUrl()));
                    }
                }
            }

            if (lastModifTimeFromLink != null && lastModifTimeFromLink.isAfter(link.getLastCheckTime())) {

                botClient.sendUpdate(
                    new LinkUpdateRequest(link.getId(), new URI(link.getUrl()), "Тут обновление" + link.getUrl(),
                        linkRepository.findByUrl(link.getUrl()).stream().map(Chat::getTgChatId).toArray(Long[]::new)
                    ));
                linkRepository.updateLinkCheckTime(link.getId(), lastModifTimeFromLink);
                count++;
            }

        }
        return count;
    }
}
