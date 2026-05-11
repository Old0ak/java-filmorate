package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

@Component
@RequiredArgsConstructor
public class DbLikeStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(Long filmId, Long userId) {
        final String sqlQuery = """
                INSERT INTO likes (film_id, user_id) VALUES (?, ?)
                """;

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void removeLike(Long userId, Long filmId) {
        final String sqlQuery = """
                DELETE FROM likes WHERE film_id = ? AND user_id = ?
                """;

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }
}
