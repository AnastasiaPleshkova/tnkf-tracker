package edu.java.scrapper.services;

import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    Collection<Link> listAll(long tgChatId);

    Collection<Chat> listAllChats(URI url);

    Link findOrCreate(String url);
}
