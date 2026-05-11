package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;

public interface GenreStorage {

    Genre getGenre(long genreId);

    List<Genre> getAll();

    void load(List<Film> films);

    void saveGenresToFilm(Film film);

    Map<Long, Genre> getGenres();

    void save(Genre genre);
}
