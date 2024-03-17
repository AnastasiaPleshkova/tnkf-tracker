package edu.java.scrapper.repositories;

import edu.java.scrapper.dto.dao.ChatDto;
import edu.java.scrapper.models.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class ChatRepositoryImpl implements ChatRepository {
    public static final String GET_ALL = "SELECT * FROM chat";

    private final JdbcTemplate jdbcTemplate;

    public Optional<Chat> find(long tgChatId) {
        return jdbcTemplate.query(GET_ALL + " WHERE tg_chat_id=?", new Object[] {tgChatId},
            new BeanPropertyRowMapper<>(Chat.class)
        ).stream().findAny();
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query(GET_ALL, new BeanPropertyRowMapper<>(Chat.class));
    }

    @Transactional
    public void add(ChatDto chatDto) {
        jdbcTemplate.update("INSERT INTO chat (tg_chat_id, created_at, created_by) VALUES (?,?,?)",
            chatDto.getTgChatId(), chatDto.getCreatedAt(), chatDto.getCreatedBy()
        );
    }

    @Transactional
    public void remove(long tgChatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE tg_chat_id=?", tgChatId);
    }
}
