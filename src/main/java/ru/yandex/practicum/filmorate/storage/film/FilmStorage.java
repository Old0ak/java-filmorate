package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Film createFilm(Film film);

    Film update(Film film);

    List<Film> getAll();

    Film getById(Long id);

    Map<Long, Film> getFilms();
}
