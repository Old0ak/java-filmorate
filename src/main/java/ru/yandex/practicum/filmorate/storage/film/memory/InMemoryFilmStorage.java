package ru.yandex.practicum.filmorate.storage.film.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validators.IdExistValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final IdExistValidator idExistValidator = new IdExistValidator();

    private Long generatedId = 0L;

    @Override
    public Film createFilm(Film film) {
        film.setId(++generatedId);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        idExistValidator.validate(film, films, "Фильм");
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(Long id) {
        idExistValidator.validate(films.get(id), films, "Фильм");
        return films.get(id);
    }

}
