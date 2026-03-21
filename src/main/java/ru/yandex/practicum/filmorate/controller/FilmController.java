package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.request.FilmRequest;
import ru.yandex.practicum.filmorate.model.response.FilmResponse;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;
    private final FilmMapper mapper;

    @PostMapping
    public FilmResponse create(@Valid @RequestBody final FilmRequest request) {
        log.info("Начато создание фильма {}", request);
        return mapper.toResponse(service.createFilm(mapper.toFilm(request)));
    }

    @PutMapping
    public FilmResponse update(@Valid @RequestBody final FilmRequest request) {
        log.info("Начато обновление фильма {}", request);
        return mapper.toResponse(service.update(mapper.toFilm(request)));
    }

    @GetMapping
    public List<FilmResponse> getAll() {
        log.info("Начато выведение всех фильмов");
        return service.getAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public FilmResponse getFilm(@PathVariable final String id) {
        log.info("Начато выведение фильма по id: {}", id);
        return mapper.toResponse(service.getFilm(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable final String id, @PathVariable final String userId) {
        log.info("Начато постановка лайка фильму с id: {}, пользователем с id: {}", id, userId);
        service.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable final String id, @PathVariable final String userId) {
        log.info("Начато удаление лайка с фильма id: {}, пользователем с id: {}", id, userId);
        service.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<FilmResponse> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Начато выведение самых популярных фильмов в количестве: {}", count);
        return service.getPopularFilms(count).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
