package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {

    private final GenreStorage genreStorage;
    private final GenreMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

    private final Map<Integer, Genre> genres = new HashMap<>();

    public GenreService(GenreStorage genreStorage, GenreMapper mapper) {
        genres.put(1, new Genre(1, "Комедия"));
        genres.put(2, new Genre(2, "Драма"));
        genres.put(3, new Genre(3, "Мультфильм"));
        genres.put(4, new Genre(4, "Триллер"));
        genres.put(5, new Genre(5, "Документальный"));
        genres.put(6, new Genre(6, "Боевик"));
        this.genreStorage = genreStorage;
        this.mapper = mapper;
    }

    public List<GenreDto> getAll() {
        return genreStorage.getAll().stream()
                .map(mapper::toGenreDto)
                .toList();
    }

    public GenreDto getGenre(String id) {
        Genre genre = genreStorage.getGenre(Long.valueOf(id));

        validatorId.validate(genre, genreStorage.getGenres(), "Жанр");
        return mapper.toGenreDto(genre);
    }

    public void saveAllGenres() {
        genres.values().forEach(genreStorage::save);
    }
}
