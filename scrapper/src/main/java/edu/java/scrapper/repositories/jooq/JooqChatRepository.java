package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.domain.jooq.Tables;
import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repositories.ChatRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional(readOnly = true)
    public Optional<Chat> find(long tgChatId) {
        return dslContext
            .selectFrom(Tables.CHAT)
            .where(Tables.CHAT.TG_CHAT_ID.eq(tgChatId))
            .fetchInto(Chat.class).stream().findAny();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        return dslContext
            .selectFrom(Tables.CHAT)
            .fetchInto(Chat.class);
    }

    @Override
    @Transactional
    public int add(ChatDto chatDto) {
        return dslContext
            .insertInto(Tables.CHAT)
            .set(Tables.CHAT.TG_CHAT_ID, chatDto.getTgChatId())
            .set(Tables.CHAT.CREATED_AT, chatDto.getCreatedAt())
            .set(Tables.CHAT.CREATED_BY, chatDto.getCreatedBy())
            .execute();
    }

    @Override
    @Transactional
    public int remove(long tgChatId) {
        return dslContext
            .deleteFrom(Tables.CHAT)
            .where(Tables.CHAT.TG_CHAT_ID.eq(tgChatId))
            .execute();
    }
}
