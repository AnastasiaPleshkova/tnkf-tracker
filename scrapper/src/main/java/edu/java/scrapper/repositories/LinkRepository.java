package edu.java.scrapper.repositories;

import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {

    Optional<Link> find(String url);

    List<Link> findAll();

    List<Link> findByChatId(long chatId);

    List<Chat> findByUrl(String url);

    List<Link> findByLastCheck(OffsetDateTime time);

    void addLink(LinkDto linkDto);

    void add(long chatId, long linkId);

    void remove(long chatId, long linkId);

    void updateLinkCheckTime(long id, OffsetDateTime time);
}
