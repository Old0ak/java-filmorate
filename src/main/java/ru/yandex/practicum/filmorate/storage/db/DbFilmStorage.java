package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.entity.FilmEntity;
import ru.yandex.practicum.filmorate.model.entity.GenreEntity;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.FilmStorageMapper;
import ru.yandex.practicum.filmorate.storage.db.mapper.MpaStorageMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Component
@RequiredArgsConstructor
public class DbFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final FilmStorageMapper mapper;
    private final MpaStorageMapper mapperMpa;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Override
    public Film createFilm(Film film) {
        FilmEntity entity = mapper.toFilmEntity(film);

        final String sqlQuery = """
                INSERT INTO films (name, description, release_date, duration, mpa_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setDate(3, Date.valueOf(entity.getReleaseDate()));
            stmt.setLong(4, entity.getDuration());

            if (entity.getMpa() != null) {
                long mpaId = entity.getMpa().getId();
                stmt.setLong(5, mpaId);
            } else {
                stmt.setNull(5, java.sql.Types.BIGINT);
            }
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        genreStorage.saveGenresToFilm(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        FilmEntity entity = mapper.toFilmEntity(film);

        final String sqlQuery = """
                UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setDate(3, Date.valueOf(entity.getReleaseDate()));
            stmt.setLong(4, entity.getDuration());
            stmt.setObject(5, entity.getMpa() != null ? entity.getMpa().getId() : null);
            stmt.setLong(6, entity.getId());
            return stmt;
        });

        return mapper.toFilm(entity);
    }

    @Override
    public List<Film> getAll() {
        final String sqlQuery = """
                SELECT
                    f.id,
                    f.name AS film_name,
                    f.description,
                    f.release_date,
                    f.duration,
                    f.mpa_id,
                    m.id AS mpa_id,
                    m.name AS mpa_name
                FROM films AS f
                LEFT JOIN mpa AS m ON f.mpa_id = m.id
                """;

        List<FilmEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            FilmEntity entity = new FilmEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            entity.setDescription(rs.getString("description"));
            entity.setReleaseDate(rs.getDate("release_date").toLocalDate());
            entity.setDuration(rs.getLong("duration"));

            long mpaId = rs.getLong("mpa_id");
            String mpaName = rs.getString("mpa_name");
            entity.setMpa(mapperMpa.toMpaEntity(new Mpa(mpaId, mpaName)));

            return entity;
        });

        return entities.stream()
                .map(mapper::toFilm)
                .toList();
    }

    @Override
    public Film getById(Long id) {
        final String sqlQuery = """
                SELECT
                    f.id,
                    f.name AS film_name,
                    f.description,
                    f.release_date,
                    f.duration,
                    f.mpa_id,
                    m.id AS mpa_id,
                    m.name AS mpa_name,
                    g.id AS genre_id,
                    g.name AS genre_name
                FROM films AS f
                LEFT JOIN mpa AS m ON f.mpa_id = m.id
                LEFT JOIN film_genres AS fg ON f.id = fg.film_id
                LEFT JOIN genres AS g ON fg.genre_id = g.id
                WHERE f.id = ?
                """;

        final FilmEntity[] entity = {null};
        List<GenreEntity> genresEntity = new ArrayList<>();

        jdbcTemplate.query(sqlQuery, new Object[]{id}, (rs, rowNum) -> {
            if (entity[0] == null) {
                entity[0] = new FilmEntity();
                entity[0].setId(rs.getLong("id"));
                entity[0].setName(rs.getString("film_name"));
                entity[0].setDescription(rs.getString("description"));
                entity[0].setReleaseDate(rs.getDate("release_date").toLocalDate());
                entity[0].setDuration(rs.getLong("duration"));

                long mpaId = rs.getLong("mpa_id");
                String mpaName = rs.getString("mpa_name");
                entity[0].setMpa(mapperMpa.toMpaEntity(new Mpa(mpaId, mpaName)));
            }

            if (rs.getObject("genre_id") != null) {
                GenreEntity genre = new GenreEntity(
                        rs.getLong("genre_id"),
                        rs.getString("genre_name")
                );
                genresEntity.add(genre);
            }

            return entity[0];
        });

        if (entity[0] == null) {
            return null;
        }

        entity[0].setGenres(genresEntity);
        return mapper.toFilm(entity[0]);
    }

    @Override
    public Map<Long, Film> getFilms() {
        final String sqlQuery = """
                SELECT id, name, description, release_date, duration, mpa_id FROM films
                """;

        List<FilmEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            FilmEntity entity = new FilmEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            entity.setDescription(rs.getString("description"));
            entity.setReleaseDate(rs.getDate("release_date").toLocalDate());
            entity.setDuration(rs.getLong("duration"));

            if (entity.getMpa() != null) {
                long mpaId = rs.getLong("mpa_id");
                entity.setMpa(mapperMpa.toMpaEntity(mpaStorage.getMpa(mpaId)));
            }
            return entity;
        });

        return entities.stream()
                .map(mapper::toFilm)
                .collect(Collectors.toMap(Film::getId, film -> film));
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String sqlQuery = """
            SELECT
                f.id AS film_id,
                f.name AS film_name,
                f.description AS description,
                f.release_date AS release_date,
                f.duration AS duration,
                m.name AS mpa_name,
                COUNT(l.film_id) AS likes_count
            FROM films f
            LEFT JOIN likes l ON f.id = l.film_id
            LEFT JOIN mpa m ON f.mpa_id = m.id
            GROUP BY f.id, f.name, f.description, f.release_date, f.duration, m.name
            ORDER BY likes_count DESC
            LIMIT ?
        """;

        List<FilmEntity> entities = jdbcTemplate.query(sqlQuery, new Object[]{count}, (rs, rowNum) -> {
           FilmEntity entity = new FilmEntity();
           entity.setId(rs.getLong("film_id"));
           return entity;
        });

        return entities.stream()
                .map(mapper::toFilm)
                .toList();
    }
}
