package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PostMapping
    public UserDto create(@Valid @RequestBody final UserDto userDto) {
        log.info("Начато создание пользователя {}", userDto);
        return service.createUser(userDto);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody final UserDto userDto) {
        log.info("Начата обновление пользователя {}", userDto);
        return service.updateUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAll() {
        log.info("Начато выведение всех пользователей");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable final String id) {
        log.info("Начато выведение пользователя с id: {}", id);
        return service.getUser(id);
    }
}
