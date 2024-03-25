package edu.java.scrapper.services.jooq;

import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.exceptions.ChatAlreadyRegistered;
import edu.java.scrapper.repositories.ChatRepository;
import edu.java.scrapper.services.ChatService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqChatService implements ChatService {

    private final ChatRepository chatRepository;

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
