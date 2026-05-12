package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;
    private final GenreMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

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
}
