package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.models.Chat;
import java.util.Collection;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByTgChatId(Long tgChatId);

    int deleteByTgChatId(Long tgChatId);

    Collection<Chat> findByLinksUrl(String url);

}
