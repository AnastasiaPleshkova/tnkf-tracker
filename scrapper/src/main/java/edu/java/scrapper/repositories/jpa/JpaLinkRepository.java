package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.Link;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrl(String url);


    boolean existsByChatsIdAndChatsLinksId(Long chatId, Long linkId);


    Collection<Link> findByChatsTgChatId(Long tgChatId);

    List<Link> findAllByOrderByLastCheckTimeAsc(Pageable pageable);







}
