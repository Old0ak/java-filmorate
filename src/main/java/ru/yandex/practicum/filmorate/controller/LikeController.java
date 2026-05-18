package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

@Slf4j
@RestController
@RequestMapping("/films/{id}/like/{userId}")
@RequiredArgsConstructor
public class LikeController {

    private final FilmService service;

    @PutMapping()
    public void addLike(@PathVariable final String id, @PathVariable final String userId) {
        log.info("Начато постановка лайка фильму с id: {}, пользователем с id: {}", id, userId);
        service.addLike(id, userId);
    }

    @DeleteMapping()
    public void removeLike(@PathVariable final String id, @PathVariable final String userId) {
        log.info("Начато удаление лайка с фильма id: {}, пользователем с id: {}", id, userId);
        service.removeLike(id, userId);
    }
}
