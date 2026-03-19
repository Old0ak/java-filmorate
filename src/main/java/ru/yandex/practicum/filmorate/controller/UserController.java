package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserHandler userHandler;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Начато создание пользователя {}", user);
        return userHandler.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Начата обновление пользователя {}", user);
        return userHandler.updateUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userHandler.getAll();
    }
}
