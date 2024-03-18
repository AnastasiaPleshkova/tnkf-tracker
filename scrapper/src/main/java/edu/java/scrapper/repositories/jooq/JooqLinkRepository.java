package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.domain.jooq.Tables;
import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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
    public List<Chat> findByUrl(String url) {
        return dslContext.select()
            .from(Tables.CHAT_LINK_MAPPING)
            .join(Tables.LINK).on(Tables.CHAT_LINK_MAPPING.LINK_ID.eq(Tables.LINK.ID))
            .join(Tables.CHAT).on(Tables.CHAT_LINK_MAPPING.CHAT_ID.eq(Tables.CHAT.ID))
            .where(Tables.LINK.URL.eq(url))
            .fetchInto(Chat.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findByLastCheck(OffsetDateTime time) {
        return dslContext.selectFrom(Tables.LINK)
            .where(Tables.LINK.LAST_CHECK_TIME.lt(time))
            .fetchInto(Link.class);
    }

    @Override
    @Transactional
    public void addLink(LinkDto linkDto) {
        dslContext.insertInto(Tables.LINK)
            .set(Tables.LINK.URL, linkDto.getUrl())
            .set(Tables.LINK.QUESTION_ID, linkDto.getQuestionId())
            .set(Tables.LINK.OWNER_NAME, linkDto.getOwnerName())
            .set(Tables.LINK.REPOSITORY_NAME, linkDto.getRepositoryName())
            .set(Tables.LINK.ANSWER_COUNT, linkDto.getAnswerCount())
            .set(Tables.LINK.COMMITS_COUNT, linkDto.getCommitsCount())
            .set(Tables.LINK.LAST_CHECK_TIME, linkDto.getLastCheckTime())
            .set(Tables.LINK.CREATED_AT, linkDto.getCreatedAt())
            .set(Tables.LINK.CREATED_BY, linkDto.getCreatedBy())
            .execute();

    }

    @Override
    @Transactional
    public void add(long chatId, long linkId) {
        dslContext.insertInto(Tables.CHAT_LINK_MAPPING)
            .set(Tables.CHAT_LINK_MAPPING.CHAT_ID, chatId)
            .set(Tables.CHAT_LINK_MAPPING.LINK_ID, linkId)
            .execute();
    }

    @Override
    @Transactional
    public void remove(long chatId, long linkId) {
        dslContext.deleteFrom(Tables.CHAT_LINK_MAPPING)
            .where(Tables.CHAT_LINK_MAPPING.CHAT_ID.eq(chatId)
                .and(Tables.CHAT_LINK_MAPPING.LINK_ID.eq(linkId)))
            .execute();
    }

    @Override
    @Transactional
    public void updateLinkCheckTime(long id, OffsetDateTime time) {
        dslContext.update(Tables.LINK)
            .set(Tables.LINK.LAST_CHECK_TIME, time)
            .where(Tables.LINK.ID.eq(id))
            .execute();
    }

    @Override
    @Transactional
    public void updateLinkCommitsCount(long id, long commits) {
        dslContext.update(Tables.LINK)
            .set(Tables.LINK.COMMITS_COUNT, commits)
            .where(Tables.LINK.ID.eq(id))
            .execute();
    }

    @Override
    @Transactional
    public void updateLinkAnswersCount(long id, long answers) {
        dslContext.update(Tables.LINK)
            .set(Tables.LINK.ANSWER_COUNT, answers)
            .where(Tables.LINK.ID.eq(id))
            .execute();
    }

}
