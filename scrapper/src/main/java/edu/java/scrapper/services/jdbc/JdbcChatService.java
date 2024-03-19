package edu.java.scrapper.services.jdbc;

import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.exceptions.ChatAlreadyRegistered;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.services.ChatService;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JdbcChatService implements ChatService {
    private final ChatRepository chatRepository;

    public JdbcChatService(@Qualifier("jooqChatRepository") ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(long tgChatId) {
        OffsetDateTime time = OffsetDateTime.now().withNano(0);
        ChatDto chat = new ChatDto(tgChatId, time, "admin");
        if (chatRepository.find(tgChatId).isEmpty()) {
            chatRepository.add(chat);
        } else {
            throw new ChatAlreadyRegistered();
        }
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.remove(tgChatId);
    }
}
