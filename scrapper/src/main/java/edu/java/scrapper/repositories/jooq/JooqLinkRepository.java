package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.domain.jooq.Tables;
import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional(readOnly = true)
    public Optional<Link> find(String url) {
        return dslContext.selectFrom(Tables.LINK)
            .where(Tables.LINK.URL.eq(url))
            .fetchOptionalInto(Link.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findAll() {
        return dslContext.selectFrom(Tables.LINK)
            .fetchInto(Link.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findByChatId(long chatId) {
        return dslContext.select()
            .from(Tables.CHAT_LINK_MAPPING)
            .join(Tables.LINK).on(Tables.CHAT_LINK_MAPPING.LINK_ID.eq(Tables.LINK.ID))
            .join(Tables.CHAT).on(Tables.CHAT_LINK_MAPPING.CHAT_ID.eq(Tables.CHAT.ID))
            .where(Tables.CHAT.TG_CHAT_ID.eq(chatId))
            .fetchInto(Link.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findChatsByUrl(String url) {
        return dslContext.select()
            .from(Tables.CHAT_LINK_MAPPING)
            .join(Tables.LINK).on(Tables.CHAT_LINK_MAPPING.LINK_ID.eq(Tables.LINK.ID))
            .join(Tables.CHAT).on(Tables.CHAT_LINK_MAPPING.CHAT_ID.eq(Tables.CHAT.ID))
            .where(Tables.LINK.URL.eq(url))
            .fetchInto(Chat.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findByLastCheckLimit(int maxUpdatedRecordsValue) {
        return dslContext.selectFrom(Tables.LINK)
            .orderBy(Tables.LINK.LAST_CHECK_TIME)
            .limit(maxUpdatedRecordsValue)
            .fetchInto(Link.class);
    }

    @Override
    @Transactional
    public void addLink(LinkDto linkDto) {
        dslContext.insertInto(Tables.LINK)
            .set(Tables.LINK.URL, linkDto.getUrl())
            .set(Tables.LINK.LAST_CHECK_TIME, linkDto.getLastCheckTime())
            .set(Tables.LINK.UPDATED_AT, linkDto.getUpdatedAt())
            .set(Tables.LINK.CREATED_AT, linkDto.getCreatedAt())
            .set(Tables.LINK.CREATED_BY, linkDto.getCreatedBy())
            .set(Tables.LINK.ANSWERS_COUNT, linkDto.getAnswersCount())
            .set(Tables.LINK.COMMITS_COUNT, linkDto.getCommitsCount())
            .execute();

    }

    @Override
    @Transactional
    public int add(long chatId, long linkId) {
        return dslContext.insertInto(Tables.CHAT_LINK_MAPPING)
            .set(Tables.CHAT_LINK_MAPPING.CHAT_ID, chatId)
            .set(Tables.CHAT_LINK_MAPPING.LINK_ID, linkId)
            .execute();
    }

    @Override
    @Transactional
    public int remove(long chatId, long linkId) {
        return dslContext.deleteFrom(Tables.CHAT_LINK_MAPPING)
            .where(Tables.CHAT_LINK_MAPPING.CHAT_ID.eq(chatId)
                .and(Tables.CHAT_LINK_MAPPING.LINK_ID.eq(linkId)))
            .execute();
    }

    @Transactional(readOnly = true)
    public boolean checkTracking(long chatId, long linkId) {
        return dslContext.fetchExists(
            DSL.selectOne()
                .from(Tables.CHAT_LINK_MAPPING)
                .where(Tables.CHAT_LINK_MAPPING.CHAT_ID.eq(chatId)
                    .and(Tables.CHAT_LINK_MAPPING.LINK_ID.eq(linkId)))
        );
    }

    @Override
    @Transactional
    public void update(Link link) {
        dslContext.update(Tables.LINK)
            .set(Tables.LINK.URL, link.getUrl())
            .set(Tables.LINK.LAST_CHECK_TIME, link.getLastCheckTime())
            .set(Tables.LINK.UPDATED_AT, link.getUpdatedAt())
            .set(Tables.LINK.ANSWERS_COUNT, link.getAnswersCount())
            .set(Tables.LINK.COMMITS_COUNT, link.getCommitsCount())
            .where(Tables.LINK.ID.eq(link.getId()))
            .execute();
    }

}
