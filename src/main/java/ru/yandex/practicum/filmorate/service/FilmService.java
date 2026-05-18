package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.exceptions.DateValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.storage.*;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final FilmMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

    public FilmDto createFilm(FilmDto filmDto) {
        Film film = mapper.toFilm(filmDto);

        validateReleaseDate(film);

        if (film.getMpa() != null) {
            validatorId.validate(film.getMpa(), mpaStorage.getMpas(), "Рейтинг MPA");
        }

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                validatorId.validate(genre, genreStorage.getGenres(), "Жанр");
            }
        }

        return mapper.toFilmDto(filmStorage.createFilm(film));
    }

    public FilmDto update(FilmDto filmDto) {
        Film film = mapper.toFilm(filmDto);

        validateReleaseDate(film);
        validatorId.validate(film, filmStorage.getFilms(), "Фильм");

        if (film.getMpa() != null) {
            validatorId.validate(film.getMpa(), mpaStorage.getMpas(), "Рейтинг MPA");
        }

        return mapper.toFilmDto(filmStorage.update(film));
    }

    public List<FilmDto> getAll() {
        return filmStorage.getAll().stream()
                .map(mapper::toFilmDto)
                .toList();
    }

    public FilmDto getFilm(String id) {
        Film film = filmStorage.getById(Long.valueOf(id));

        validatorId.validate(film, filmStorage.getFilms(), "Фильм");
        return mapper.toFilmDto(film);
    }

    public void addLike(String id, String userId) {
        Film film = filmStorage.getById(Long.valueOf(id));
        User user = userStorage.getById(Long.valueOf(userId));

        validatorId.validate(film, filmStorage.getFilms(), "Фильм");
        validatorId.validate(user, userStorage.getUsers(), "Пользователь");

        likeStorage.addLike(film.getId(), user.getId());
    }

    public void removeLike(String id, String userId) {
        Film film = filmStorage.getById(Long.valueOf(id));
        User user = userStorage.getById(Long.valueOf(userId));

        validatorId.validate(film, filmStorage.getFilms(), "Фильм");
        validatorId.validate(user, userStorage.getUsers(), "Пользователь");

        likeStorage.removeLike(film.getId(), user.getId());
    }

    public List<FilmDto> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count).stream()
                .map(mapper::toFilmDto)
                .toList();
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new DateValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }
}
