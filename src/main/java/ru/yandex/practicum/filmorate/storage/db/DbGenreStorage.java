package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.GenreEntity;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.GenreStorageMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DbGenreStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private final GenreStorageMapper mapper;

    @Override
    public void saveGenresToFilm(Film film) {
        final long filmId = film.getId();
         final String sqlDelete = """
                DELETE FROM film_genres WHERE film_id = ?
                """;
        jdbcTemplate.update(sqlDelete, filmId);

        final List<Genre> genres = film.getGenres();
        if (genres == null || genres.isEmpty()) {
            return;
        }

        final String checkSql = "SELECT COUNT(*) FROM film_genres WHERE film_id = ? AND genre_id = ?";
        for (Genre genre : genres) {
            int count = jdbcTemplate.queryForObject(checkSql, Integer.class, filmId, genre.getId());

            if (count == 0) {
                jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)", filmId, genre.getId());
            } else {
                System.out.println("Запись уже существует.");
            }
        }
    }

    @Override
    public Genre getGenre(long genreId) {
        final String sqlQuery = """
                SELECT id, name FROM genres WHERE id = ?
                """;

        List<GenreEntity> entities = jdbcTemplate.query(sqlQuery, new Object[]{genreId}, (rs, rowNum) -> {
            GenreEntity entity = new GenreEntity();
            entity.setId(rs.getInt("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });

        return entities.isEmpty() ? null : mapper.toGenre(entities.get(0));
    }

    @Override
    public List<Genre> getAll() {
        final String sqlQuery = """
                SELECT id, name FROM genres
                """;

        List<GenreEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            GenreEntity entity = new GenreEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });
        return entities.stream()
                .map(mapper::toGenre)
                .toList();
    }

    @Override
    public void load(List<Film> films) {

    }

    @Override
    public Map<Long, Genre> getGenres() {
        final String sqlQuery = """
                SELECT id, name FROM genres
                """;

        List<GenreEntity> entities = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            GenreEntity entity = new GenreEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });

        return entities.stream()
                .map(mapper::toGenre)
                .collect(Collectors.toMap(Genre::getId, genre -> genre));
    }

    @Override
    public void save(Genre genre) {
        try {
            String insertSql = "INSERT INTO genres (id, name) VALUES (?, ?)";
            jdbcTemplate.update(insertSql, genre.getId(), genre.getName());
        } catch (DuplicateKeyException e) {
            String updateSql = "UPDATE genres SET name = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, genre.getName(), genre.getId());
        }
    }
}
