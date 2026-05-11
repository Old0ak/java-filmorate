package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;

    @PostMapping
    public FilmDto create(@Valid @RequestBody final FilmDto filmDto) {
        log.info("Начато создание фильма {}", filmDto);
        return service.createFilm(filmDto);
    }

    @PutMapping
    public FilmDto update(@Valid @RequestBody final FilmDto filmDto) {
        log.info("Начато обновление фильма {}", filmDto);
        return service.update(filmDto);
    }

    @GetMapping
    public List<FilmDto> getAll() {
        log.info("Начато выведение всех фильмов");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FilmDto getFilm(@PathVariable final String id) {
        log.info("Начато выведение фильма по id: {}", id);
        return service.getFilm(id);
    }

    @GetMapping("/popular")
    public List<FilmDto> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Начато выведение самых популярных фильмов в количестве: {}", count);
        return service.getPopularFilms(count);
    }
}
