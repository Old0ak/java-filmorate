package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.entity.UserEntity;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.UserStorageMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbFriendStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    private final UserStorageMapper mapper;

    @Override
    public void addFriend(Long userId, Long friendId) {
        final String sqlQuery = """
                INSERT INTO friends(user_id, friend_id)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends(Long id) {
        final String sqlQuery = """
            SELECT u.id, u.email, u.login, u.name, u.birthday
            FROM friends AS f
            JOIN users AS u ON f.friend_id = u.id
            WHERE f.user_id = ?
        """;

        List<UserEntity> entities = jdbcTemplate.query(sqlQuery, new Object[]{id}, (rs, rowNum) -> {
            UserEntity entity = new UserEntity();
            entity.setId(rs.getLong("id"));
            entity.setEmail(rs.getString("email"));
            entity.setLogin(rs.getString("login"));
            entity.setName(rs.getString("name"));
            entity.setBirthday(rs.getDate("birthday").toLocalDate());
            return entity;
        });

        return entities.stream()
                .map(mapper::toUser)
                .toList();
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        final String sqlQuery = """
                DELETE FROM friends WHERE user_id = ? AND friend_id = ?
                """;

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        final String sqlQuery = """
                SELECT u.id, u.email, u.login, u.name, u.birthday
                FROM friends AS f1
                JOIN friends AS f2 ON f1.friend_id = f2.friend_id
                JOIN users AS u ON f1.friend_id = u.id
                WHERE f1.user_id = ? AND f2.user_id = ?
                """;

        List<UserEntity> entities = jdbcTemplate.query(sqlQuery,
                new Object[]{userId, otherUserId}, (rs, rowNum) -> {
            UserEntity entity = new UserEntity();
            entity.setId(rs.getLong("id"));
            entity.setEmail(rs.getString("email"));
            entity.setLogin(rs.getString("login"));
            entity.setName(rs.getString("name"));
            entity.setBirthday(rs.getDate("birthday").toLocalDate());
            return entity;
        });

        return entities.stream()
                .map(mapper::toUser)
                .toList();
    }
}
