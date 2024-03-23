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
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;

    @Override
    public Link add(long chatId, URI url) {
        Chat chat = chatRepository.find(chatId).orElseThrow(ResourceNotFound::new);
        Link link = findOrCreate(url.toString());

        if (linkRepository.checkTracking(chat.getId(), link.getId())) {
            throw new LinkAlreadyTracked();
        }

        linkRepository.add(chat.getId(), link.getId());
        return link;
    }

    @Override
    public Link remove(long chatId, URI url) {
        Chat chat = chatRepository.find(chatId).orElseThrow(ResourceNotFound::new);
        Link linkToDelete = linkRepository.find(url.toString()).orElseThrow(ResourceNotFound::new);

        linkRepository.remove(chat.getId(), linkToDelete.getId());
        return linkToDelete;
    }

    @Override
    public Collection<Link> listAll(long chatId) {
        return linkRepository.findByChatId(chatId);
    }

    @Override
    public Collection<Chat> listAllChats(URI url) {
        return linkRepository.findChatsByUrl(url.toString());
    }

    public Link findOrCreate(String url) {
        Optional<Link> link = linkRepository.find(url);
        if (link.isPresent()) {
            return link.get();
        }

        linkRepository.addLink(createDto(url));
        link = linkRepository.find(url);
        return link.get();
    }

    private LinkDto createDto(String url) {
        OffsetDateTime time = OffsetDateTime.now().withNano(0);
        LinkDto linkToCreate = new LinkDto(url, time, time, time, "admin");
        return linkToCreate;
    }

}
