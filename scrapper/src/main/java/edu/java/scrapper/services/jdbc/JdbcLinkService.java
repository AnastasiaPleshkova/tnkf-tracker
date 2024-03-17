package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.dto.response.client.GitCommitsResponse;
import edu.java.scrapper.exceptions.LinkAlreadyTracked;
import edu.java.scrapper.exceptions.ResourceNotFound;
import edu.java.scrapper.exceptions.UnsupportedLink;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.StackClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final StackClient stackClient;
    private final GitClient gitClient;

    @Override
    public Link add(long chatId, URI url) {
        Chat chat = chatRepository.find(chatId).orElseThrow(ResourceNotFound::new);
        Optional<Link> link = linkRepository.find(url.toString());

        if (link.isEmpty()) {
            OffsetDateTime time = OffsetDateTime.now().withNano(0);
            LinkDto linkToCreate = new LinkDto(url.toString(), "", "", "", (long) 0,
                time, (long) 0, time, "admin"
            );
            if (url.toString().startsWith("https://stackoverflow.com")) {
                Pattern pattern = Pattern.compile("https://stackoverflow.com/questions/(\\d+)/.*");
                Matcher matcher = pattern.matcher(url.toString());
                if (matcher.find()) {
                    linkToCreate.setQuestionId(matcher.group(1));
                }
                int answerCount = stackClient.fetchQuestion(linkToCreate.getQuestionId()).items().get(0).answerCount();
                linkToCreate.setAnswerCount((long) answerCount);
            } else if (url.toString().startsWith("https://github.com")) {
                Pattern pattern = Pattern.compile("https://github.com/([^/]+)/([^/]+)");
                Matcher matcher = pattern.matcher(url.toString());
                if (matcher.find()) {
                    linkToCreate.setOwnerName(matcher.group(1));
                    linkToCreate.setRepositoryName(matcher.group(2));
                }
                GitCommitsResponse[] gitCommitsResponses =
                    gitClient.fetchUserRepoCommits(linkToCreate.getOwnerName(), linkToCreate.getRepositoryName());
                linkToCreate.setCommitsCount((long) gitCommitsResponses.length);
            }
            linkRepository.addLink(linkToCreate);
            link = linkRepository.find(url.toString());
        } else {
            throw new UnsupportedLink();
        }

        if (linkRepository.findByChatId(chatId).stream()
            .anyMatch(linkAlreadyTracked -> linkAlreadyTracked.getUrl().equals(url.toString()))) {
            throw new LinkAlreadyTracked();
        }

        linkRepository.add(chat.getId(), link.get().getId());
        return link.get();
    }

    @Override
    public Link remove(long chatId, URI url) {
        Chat chat = chatRepository.find(chatId).orElseThrow(ResourceNotFound::new);
        List<Link> byChatId = linkRepository.findByChatId(chatId);
        Optional<Link> linkToDelete = byChatId.stream()
            .filter(x -> x.getUrl().equals(url.toString())).findAny();

        if (linkToDelete.isEmpty()) {
            throw new ResourceNotFound();
        }

        linkRepository.remove(chat.getId(), linkToDelete.get().getId());
        return linkToDelete.get();
    }

    @Override
    public Collection<Link> listAll(long chatId) {
        return linkRepository.findByChatId(chatId);
    }

    @Override
    public Collection<Chat> listAllChats(URI url) {
        return linkRepository.findByUrl(url.toString());
    }

}
