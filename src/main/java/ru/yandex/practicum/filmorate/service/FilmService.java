package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.exceptions.DateValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

    public FilmDto createFilm(FilmDto filmDto) {
        Film film = mapper.toFilm(filmDto);

        validateReleaseDate(film);
        return mapper.toFilmDto(filmStorage.createFilm(film));
    }

    public FilmDto update(FilmDto filmDto) {
        Film film = mapper.toFilm(filmDto);

        validateReleaseDate(film);
        validatorId.validate(film, filmStorage.getFilms(), "Фильм");
        return mapper.toFilmDto( filmStorage.update(film));
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
        film.getLikes().add(user);
    }

    public void removeLike(String id, String userId) {
        Film film = filmStorage.getById(Long.valueOf(id));
        User user = userStorage.getById(Long.valueOf(userId));

        validatorId.validate(film, filmStorage.getFilms(), "Фильм");
        validatorId.validate(user, userStorage.getUsers(), "Пользователь");

        film.getLikes().remove(user);
    }

    public List<FilmDto> getPopularFilms(int count) {
        List<Film> films = filmStorage.getAll();

        List<FilmDto> sortedFilms = films.stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .map(mapper::toFilmDto)
                .toList();

        return sortedFilms.subList(0, Math.min(count, sortedFilms.size()));
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new DateValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }
}
