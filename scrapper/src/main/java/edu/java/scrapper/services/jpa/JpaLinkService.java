package edu.java.scrapper.services.jpa;

import edu.java.scrapper.exceptions.LinkAlreadyTracked;
import edu.java.scrapper.exceptions.ResourceNotFound;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.services.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        Chat chat = jpaChatRepository.findByTgChatId(tgChatId).orElseThrow(ResourceNotFound::new);
        Link link = findOrCreate(url.toString());

        if (jpaLinkRepository.existsByChatsIdAndChatsLinksId(chat.getId(), link.getId())) {
            throw new LinkAlreadyTracked();
        }

        Set<Link> trackedLinks = chat.getLinks();
        trackedLinks.add(link);
        chat.setLinks(trackedLinks);
        jpaChatRepository.save(chat);

        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Chat chat = jpaChatRepository.findByTgChatId(tgChatId).orElseThrow(ResourceNotFound::new);
        Link link = jpaLinkRepository.findByUrl(url.toString()).orElseThrow(ResourceNotFound::new);

        if (jpaLinkRepository.existsByChatsIdAndChatsLinksId(chat.getId(), link.getId())) {
            Set<Link> trackedLinks = chat.getLinks();
            trackedLinks.remove(link);
            jpaChatRepository.save(chat);
        }
        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return jpaLinkRepository.findByChatsTgChatId(tgChatId).stream().toList();
    }

    @Override
    public Collection<Chat> listAllChats(URI url) {
        return jpaChatRepository.findByLinksUrl(url.toString());
    }

    Link findOrCreate(String url) {
        Optional<Link> link = jpaLinkRepository.findByUrl(url);
        if (link.isPresent()) {
            return link.get();
        }
        jpaLinkRepository.save(createLink(url));
        link = jpaLinkRepository.findByUrl(url);
        return link.get();
    }

    private Link createLink(String url) {
        Link linkToCreate = new Link();
        linkToCreate.setUrl(url);
        return linkToCreate;
    }

}
