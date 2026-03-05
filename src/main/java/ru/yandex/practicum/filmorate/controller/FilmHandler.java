package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.DateValidationException;
import ru.yandex.practicum.filmorate.exceptions.IdNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.IdExistValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmHandler {

    private final Map<Long, Film> films = new HashMap<>();
    private final IdExistValidator idExistValidator = new IdExistValidator();

    private Long generatedId = 0L;

    public Film createFilm(@Valid Film film) {
        validateReleaseDate(film);

        film.setId(++generatedId);
        films.put(film.getId(), film);
        return film;
    }

    public Film update(@Valid Film film) {
        idExistValidator.validate(film, films, "Фильм");
        validateReleaseDate(film);

        films.put(film.getId(), film);
        return film;
    }


    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new DateValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }
}
