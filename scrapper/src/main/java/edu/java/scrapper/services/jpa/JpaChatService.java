package edu.java.scrapper.services.jpa;

import edu.java.scrapper.exceptions.ChatAlreadyRegistered;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.services.ChatService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;

    @Transactional
    @Override
    public void register(long tgChatId) {
        if (jpaChatRepository.findByTgChatId(tgChatId).isEmpty()) {
            Chat chat = new Chat();
            chat.setTgChatId(tgChatId);
            chat.setCreatedAt(OffsetDateTime.now());
            chat.setCreatedBy("admin");
            jpaChatRepository.save(chat);
        } else {
            throw new ChatAlreadyRegistered();
        }
    }

    @Transactional
    @Override
    public void unregister(long tgChatId) {
        jpaChatRepository.deleteByTgChatId(tgChatId);
    }
}
