package edu.java.scrapper.repositories;

import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.models.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    Optional<Chat> find(long tgChatId);

    List<Chat> findAll();

    void add(ChatDto chatDto);

    void remove(long tgChatId);
}
