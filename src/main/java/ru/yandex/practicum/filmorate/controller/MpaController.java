package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService service;

    @GetMapping
    public List<MpaDto> getAll() {
        log.info("Начато выведение рейтинга MPA");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MpaDto getMpa(@PathVariable final String id) {
        log.info("Начато выведение рейтинга MPA с id: {}", id);
        return service.getMpa(id);
    }
}
