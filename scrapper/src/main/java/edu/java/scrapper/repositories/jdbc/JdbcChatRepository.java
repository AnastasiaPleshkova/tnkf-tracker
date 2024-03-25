package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.models.Chat;
import edu.java.scrapper.repositories.ChatRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class JdbcChatRepository implements ChatRepository {
    public static final String GET_ALL = "SELECT * FROM chat";

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public Optional<Chat> find(long tgChatId) {
        return jdbcTemplate.query(GET_ALL + " WHERE tg_chat_id=?", new Object[] {tgChatId},
            new BeanPropertyRowMapper<>(Chat.class)
        ).stream().findAny();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        return jdbcTemplate.query(GET_ALL, new BeanPropertyRowMapper<>(Chat.class));
    }

    @Override
    @Transactional
    public int add(ChatDto chatDto) {
        return jdbcTemplate.update("INSERT INTO chat (tg_chat_id, created_at, created_by) VALUES (?,?,?)",
            chatDto.getTgChatId(), chatDto.getCreatedAt(), chatDto.getCreatedBy()
        );
    }

    @Override
    @Transactional
    public int remove(long tgChatId) {
        return jdbcTemplate.update("DELETE FROM chat WHERE tg_chat_id=?", tgChatId);
    }
}
