package edu.java.scrapper.repositories;

import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkRepositoryImpl implements LinkRepository {
    private static final String GET_ALL_LINK = "SELECT * FROM link";
    private static final String WHERE_URL = " WHERE url = ?";
    private static final String GET_ALL_JOIN = """
        SELECT * FROM chat_link_mapping
        JOIN link ON chat_link_mapping.link_id = link.id
        JOIN chat ON chat_link_mapping.chat_id = chat.id
        """;

    private final JdbcTemplate jdbcTemplate;

    public Optional<Link> find(String url) {
        return jdbcTemplate.query(GET_ALL_LINK + WHERE_URL,
            new Object[] {url}, new BeanPropertyRowMapper<>(Link.class)
        ).stream().findAny();
    }

    public List<Link> findAll() {
        return jdbcTemplate.query(GET_ALL_LINK, new BeanPropertyRowMapper<>(Link.class));
    }

    public List<Link> findByChatId(long tgChatId) {
        return jdbcTemplate.query(
            GET_ALL_JOIN + " WHERE tg_chat_id = ?",
            new Object[] {tgChatId}, new BeanPropertyRowMapper<>(Link.class)
        );
    }

    public List<Chat> findByUrl(String url) {
        return jdbcTemplate.query(GET_ALL_JOIN + WHERE_URL,
            new Object[] {url}, new BeanPropertyRowMapper<>(Chat.class)
        );
    }

    public List<Link> findByLastCheck(OffsetDateTime time) {
        return jdbcTemplate.query(GET_ALL_LINK + " WHERE last_check_time < ?",
            new Object[] {time}, new BeanPropertyRowMapper<>(Link.class)
        );
    }

    @Transactional
    public void addLink(LinkDto linkDto) {
        jdbcTemplate.update("""
                INSERT INTO link (url, question_id, owner_name, repository_name, answer_count,
                commits_count, last_check_time, created_at, created_by) VALUES (?,?,?,?,?,?,?,?,?)
                """,
            linkDto.getUrl(), linkDto.getQuestionId(), linkDto.getOwnerName(), linkDto.getRepositoryName(),
            linkDto.getAnswerCount(), linkDto.getCommitsCount(),
            linkDto.getLastCheckTime(), linkDto.getCreatedAt(), linkDto.getCreatedBy()
        );

    }

    @Transactional
    public void updateLinkCheckTime(long id, OffsetDateTime time) {
        jdbcTemplate.update("UPDATE link SET last_check_time = ? WHERE id = ?", time, id);
    }

    @Transactional
    public void updateLinkCommitsCount(long id, long commits) {
        jdbcTemplate.update("UPDATE link SET commits_count = ? WHERE id = ?", commits, id);
    }

    @Transactional
    public void updateLinkAnswersCount(long id, long answers) {
        jdbcTemplate.update("UPDATE link SET answers_count = ? WHERE id = ?", answers, id);
    }

    @Transactional
    public void add(long chatId, long linkId) {
        jdbcTemplate.update(
            "INSERT INTO chat_link_mapping (chat_id, link_id) VALUES (?,?)", chatId, linkId
        );
    }

    @Transactional
    public void remove(long chatId, long linkId) {
        jdbcTemplate.update("DELETE FROM chat_link_mapping WHERE chat_id=? AND link_id=?", chatId, linkId);
    }
}
