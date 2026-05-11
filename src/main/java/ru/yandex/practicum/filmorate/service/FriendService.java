package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendStorage friendStorage;
    private final UserStorage userStorage;
    private final UserMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

    public void addFriend(String id, String friendId) {
        User user = userStorage.getById(Long.valueOf(id));
        User friend = userStorage.getById(Long.valueOf(friendId));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validatorId.validate(friend, userStorage.getUsers(), "Пользователь");

        friendStorage.addFriend(user.getId(), friend.getId());
    }

    public void removeFriend(String id, String friendId) {
        User user = userStorage.getById(Long.valueOf(id));
        User friend = userStorage.getById(Long.valueOf(friendId));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validatorId.validate(friend, userStorage.getUsers(), "Пользователь");

        friendStorage.removeFriend(user.getId(), friend.getId());
    }

    public List<UserDto> getUserFriends(String id) {
        User user = userStorage.getById(Long.valueOf(id));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        return friendStorage.getFriends(user.getId()).stream()
                .map(mapper::toUserDto)
                .toList();
    }

    public List<UserDto> getCommonFriends(String id, String otherId) {
        User user = userStorage.getById(Long.valueOf(id));
        User otherUser = userStorage.getById(Long.valueOf(otherId));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validatorId.validate(otherUser, userStorage.getUsers(), "Пользователь");

        return friendStorage.getCommonFriends(user.getId(), otherUser.getId()).stream()
                .map(mapper::toUserDto)
                .toList();
    }
}
