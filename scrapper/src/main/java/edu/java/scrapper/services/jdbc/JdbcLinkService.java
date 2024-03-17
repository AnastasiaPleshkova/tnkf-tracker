package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.exceptions.LinkAlreadyTracked;
import edu.java.scrapper.exceptions.ResourceNotFound;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.repositories.LinkRepository;
import edu.java.scrapper.services.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;

    @Override
    public Link add(long chatId, URI url) {
        Chat chat = chatRepository.find(chatId).orElseThrow(ResourceNotFound::new);
        Optional<Link> link = linkRepository.find(url.toString());

        if (link.isEmpty()) {
            OffsetDateTime time = OffsetDateTime.now().withNano(0);
            linkRepository.addLink(new LinkDto(url.toString(), time, time, "admin"));
            link = linkRepository.find(url.toString());
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
