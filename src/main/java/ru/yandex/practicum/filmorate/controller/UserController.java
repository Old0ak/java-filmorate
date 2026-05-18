package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final FriendService friendService;


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

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable final String id, @PathVariable final String friendId) {
        log.info("Начато добавление в друзья пользователем с id: {}, нового друга с id: {}", id, friendId);
        friendService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable final String id, @PathVariable final String friendId) {
        log.info("Начато удаление из друзей пользователем с id: {}, бывшего друга с id: {}", id, friendId);
        friendService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<UserDto> getUserFriends(@PathVariable final String id) {
        log.info("Начато выведение друзей пользователя с id: {}", id);
        return friendService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> getCommonFriends(@PathVariable final String id, @PathVariable final String otherId) {
        log.info("Начато выведение общих друзей пользователя с id: {} и другого пользователя с id: {}", id, otherId);
        return friendService.getCommonFriends(id, otherId);
    }
}
