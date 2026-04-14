package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{id}/friends")
@RequiredArgsConstructor
public class FriendController {

    private final UserService service;

    @PutMapping("/{friendId}")
    public void addFriend(@PathVariable final String id, @PathVariable final String friendId) {
        log.info("Начато добавление в друзья пользователем с id: {}, нового друга с id: {}", id, friendId);
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void removeFriend(@PathVariable final String id, @PathVariable final String friendId) {
        log.info("Начато удаление из друзей пользователем с id: {}, бывшего друга с id: {}", id, friendId);
        service.removeFriend(id, friendId);
    }

    @GetMapping()
    public List<UserDto> getUserFriends(@PathVariable final String id) {
        log.info("Начато выведение друзей пользователя с id: {}", id);
        return service.getUserFriends(id);
    }

    @GetMapping("/common/{otherId}")
    public List<UserDto> getCommonFriends(@PathVariable final String id, @PathVariable final String otherId) {
        log.info("Начато выведение общих друзей пользователя с id: {} и другого пользователя с id: {}", id, otherId);
        return service.getCommonFriends(id, otherId);
    }
}
