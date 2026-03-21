package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.request.UserRequest;
import ru.yandex.practicum.filmorate.model.response.UserResponse;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PostMapping
    public UserResponse create(@Valid @RequestBody final UserRequest request) {
        log.info("Начато создание пользователя {}", request);
        return mapper.toResponse(service.createUser(mapper.toUser(request)));
    }

    @PutMapping
    public UserResponse update(@Valid @RequestBody final UserRequest request) {
        log.info("Начата обновление пользователя {}", request);
        return mapper.toResponse(service.updateUser(mapper.toUser(request)));
    }

    @GetMapping
    public List<UserResponse> getAll() {
        log.info("Начато выведение всех пользователей");
        return service.getAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable final String id) {
        log.info("Начато выведение пользователя с id: {}", id);
        return mapper.toResponse(service.getUser(id));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable final String id, @PathVariable final String friendId) {
        log.info("Начато добавление в друзья пользователем с id: {}, нового друга с id: {}", id, friendId);
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable final String id, @PathVariable final String friendId) {
        log.info("Начато удаление из друзей пользователем с id: {}, бывшего друга с id: {}", id, friendId);
        service.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<UserResponse> getUserFriends(@PathVariable final String id) {
        log.info("Начато выведение друзей пользователя с id: {}", id);
        return service.getUserFriends(id).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserResponse> getCommonFriends(@PathVariable final String id, @PathVariable final String otherId) {
        log.info("Начато выведение общих друзей пользователя с id: {} и другого пользователя с id: {}", id, otherId);
        return service.getCommonFriends(id, otherId).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
