package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.DateValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film createFilm(Film film) {
        validateReleaseDate(film);
        return filmStorage.createFilm(film);
    }

    public Film update(Film film) {
        validateReleaseDate(film);
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film getFilm(String id) {
        return filmStorage.getById(Long.valueOf(id));
    }

    public void addLike(String id, String userId) {
        Film film = filmStorage.getById(Long.valueOf(id));
        User user = userStorage.getById(Long.valueOf(userId));

        film.getLikes().add(user);
    }

    public void removeLike(String id, String userId) {
        Film film = filmStorage.getById(Long.valueOf(id));
        User user = userStorage.getById(Long.valueOf(userId));

        film.getLikes().remove(user);
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> films = getAll();

        List<Film> sortedFilms = films.stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .toList();

        return sortedFilms.subList(0, Math.min(count, sortedFilms.size()));
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new DateValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }
}
