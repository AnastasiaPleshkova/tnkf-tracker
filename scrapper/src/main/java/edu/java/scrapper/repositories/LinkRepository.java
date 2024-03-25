package edu.java.scrapper.repositories;

import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {

    Optional<Link> find(String url);

    List<Link> findAll();

    List<Link> findByChatId(long chatId);

    List<Chat> findChatsByUrl(String url);

    List<Link> findByLastCheckLimit(int value);

    void addLink(LinkDto linkDto);

    int add(long chatId, long linkId);

    int remove(long chatId, long linkId);

    boolean checkTracking(long chatId, long linkId);

    void update(Link link);

}
