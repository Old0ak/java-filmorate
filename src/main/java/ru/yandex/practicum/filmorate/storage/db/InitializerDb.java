package ru.yandex.practicum.filmorate.storage.db;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

@Component
@AllArgsConstructor
public class InitializerDb {

    private final MpaService mpaService;
    private final GenreService genreService;

    @PostConstruct
    public void init() {
        mpaService.saveAllMpas();
        genreService.saveAllGenres();
    }
}
