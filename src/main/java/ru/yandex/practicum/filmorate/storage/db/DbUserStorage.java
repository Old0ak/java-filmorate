package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.entity.UserEntity;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.UserStorageMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Primary
@Component
@RequiredArgsConstructor
public class DbUserStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    private final UserStorageMapper mapper;

    @Override
    public User createUser(User user) {
        UserEntity entity = mapper.toUserEntity(user);

        final String sqlQuery = """
                INSERT INTO users (email, login, name, birthday)
                VALUES (?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, entity.getEmail());
            stmt.setString(2, entity.getLogin());
            stmt.setString(3, entity.getName());
            stmt.setDate(4, Date.valueOf(entity.getBirthday()));
            return stmt;
        }, keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return mapper.toUser(entity);
    }

    @Override
    public User updateUser(User user) {
        UserEntity entity = mapper.toUserEntity(user);

        final String sqlQuery = """
                UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?
                """;

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, entity.getEmail());
            stmt.setString(2, entity.getLogin());
            stmt.setString(3, entity.getName());
            stmt.setDate(4, Date.valueOf(entity.getBirthday()));
            stmt.setLong(5, user.getId());
            return stmt;
        });

        return mapper.toUser(entity);
    }

    @Override
    public List<User> getAll() {
        final String sqlQuery = """
                SELECT id, email, login, name, birthday FROM users
                """;

        List<UserEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
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
    public User getById(Long id) {
        final String sqlQuery = """
                SELECT id, email, login, name, birthday FROM users WHERE id = ?
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

        return entities.isEmpty() ? null : mapper.toUser(entities.get(0));
    }

    @Override
    public Map<Long, User> getUsers() {
        final String sqlQuery = """
            SELECT id, email, login, name, birthday FROM users
            """;

        List<UserEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
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
                .collect(Collectors.toMap(User::getId, user -> user));
    }
}
