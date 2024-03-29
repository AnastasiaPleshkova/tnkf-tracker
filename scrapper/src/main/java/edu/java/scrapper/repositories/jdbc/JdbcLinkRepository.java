package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.dto.dao.LinkDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.repositories.LinkRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private static final String GET_ALL_LINK = "SELECT * FROM link";
    private static final String WHERE_URL = " WHERE url = ?";
    private static final String GET_ALL_JOIN = """
        SELECT * FROM chat_link_mapping
        JOIN link ON chat_link_mapping.link_id = link.id
        JOIN chat ON chat_link_mapping.chat_id = chat.id
        """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public Optional<Link> find(String url) {
        return jdbcTemplate.query(GET_ALL_LINK + WHERE_URL,
            new Object[] {url}, new BeanPropertyRowMapper<>(Link.class)
        ).stream().findAny();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findAll() {
        return jdbcTemplate.query(GET_ALL_LINK, new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findByChatId(long tgChatId) {
        return jdbcTemplate.query(
            GET_ALL_JOIN + " WHERE tg_chat_id = ?",
            new Object[] {tgChatId}, new BeanPropertyRowMapper<>(Link.class)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findChatsByUrl(String url) {
        return jdbcTemplate.query(GET_ALL_JOIN + WHERE_URL,
            new Object[] {url}, new BeanPropertyRowMapper<>(Chat.class)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findByLastCheckLimit(int maxUpdatedRecordsValue) {
        return jdbcTemplate.query(GET_ALL_LINK
                + " ORDER BY last_check_time LIMIT ?",
            new Object[] {maxUpdatedRecordsValue}, new BeanPropertyRowMapper<>(Link.class)
        );
    }

    @Override
    @Transactional
    public void addLink(LinkDto linkDto) {
        jdbcTemplate.update(
            """
                INSERT INTO link (url,
                                last_check_time,
                                updated_at,
                                created_at,
                                created_by,
                                answers_count,
                                commits_count)
                VALUES (?,?,?,?,?,?,?)""",
            linkDto.getUrl(),
            linkDto.getLastCheckTime(),
            linkDto.getUpdatedAt(),
            linkDto.getCreatedAt(),
            linkDto.getCreatedBy(),
            linkDto.getAnswersCount(),
            linkDto.getCommitsCount()
        );
    }

    @Override
    @Transactional
    public int add(long chatId, long linkId) {
        return jdbcTemplate.update(
            "INSERT INTO chat_link_mapping (chat_id, link_id) VALUES (?,?)", chatId, linkId
        );
    }

    @Override
    @Transactional
    public int remove(long chatId, long linkId) {
        return jdbcTemplate.update("DELETE FROM chat_link_mapping WHERE chat_id=? AND link_id=?", chatId, linkId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkTracking(long chatId, long linkId) {
        return jdbcTemplate.queryForObject("""
                SELECT EXISTS
                    (SELECT 1 FROM chat_link_mapping WHERE chat_id = ? AND link_id = ?)""",
            Boolean.class, chatId, linkId
        );
    }

    @Override
    @Transactional
    public void update(Link link) {
        jdbcTemplate.update("""
                UPDATE link
                SET url = ?,
                    last_check_time = ?,
                    updated_at = ?,
                    answers_count = ?,
                    commits_count = ?
                WHERE id = ?
                """, link.getUrl(), link.getLastCheckTime(), link.getUpdatedAt(),
            link.getAnswersCount(), link.getCommitsCount(), link.getId()
        );
    }

}
