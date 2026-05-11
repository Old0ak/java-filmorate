package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.entity.MpaEntity;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.MpaStorageMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DbMpaStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    private final MpaStorageMapper mapper;

    @Override
    public Mpa getMpa(long id) {
        final String sqlQuery = """
                SELECT id, name FROM mpa WHERE id = ?
                """;

        List<MpaEntity> entities = jdbcTemplate.query(sqlQuery, new Object[]{id}, (rs, rowNum) -> {
            MpaEntity entity = new MpaEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });

        return entities.isEmpty() ? null : mapper.toMpa(entities.get(0));
    }

    @Override
    public void save(Mpa mpa) {
        try {
            String insertSql = "INSERT INTO mpa (id, name) VALUES (?, ?)";
            jdbcTemplate.update(insertSql, mpa.getId(), mpa.getName());
        } catch (DuplicateKeyException e) {
            String updateSql = "UPDATE mpa SET name = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, mpa.getName(), mpa.getId());
        }
    }

    @Override
    public Map<Long, Mpa> getMpas() {
        final String sqlQuery = """
                SELECT id, name FROM mpa
                """;
        List<MpaEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            MpaEntity entity = new MpaEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });

        return entities.stream()
                .map(mapper::toMpa)
                .collect(Collectors.toMap(Mpa::getId, mpa -> mpa));
    }

    @Override
    public List<Mpa> getAll() {
        final String sqlQuery = """
                SELECT id, name FROM mpa
                """;

        List<MpaEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            MpaEntity entity = new MpaEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });

        return entities.stream()
                .map(mapper::toMpa)
                .toList();
    }
}
