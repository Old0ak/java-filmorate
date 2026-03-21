package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final UserMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

    public UserDto createUser(UserDto userDto) {
        User user = mapper.toUser(userDto);

        validateNameIsNull(user);
        return mapper.toUserDto(userStorage.createUser(user));
    }

    public UserDto updateUser(UserDto userDto) {
        User user = mapper.toUser(userDto);

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validateNameIsNull(user);
        return mapper.toUserDto(userStorage.updateUser(user));
    }

    public List<UserDto> getAll() {
        return userStorage.getAll().stream()
                .map(mapper::toUserDto)
                .toList();
    }

    public UserDto getUser(String id) {
        User user = userStorage.getById(Long.valueOf(id));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        return mapper.toUserDto(user);
    }

    public void addFriend(String id, String friendId) {
        User user = userStorage.getById(Long.valueOf(id));
        User friend = userStorage.getById(Long.valueOf(friendId));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validatorId.validate(friend, userStorage.getUsers(), "Пользователь");

        user.getFriends().add(friend);
        friend.getFriends().add(user);
    }

    public void removeFriend(String id, String friendId) {
        User user = userStorage.getById(Long.valueOf(id));
        User friend = userStorage.getById(Long.valueOf(friendId));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validatorId.validate(friend, userStorage.getUsers(), "Пользователь");

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
    }

    public List<UserDto> getUserFriends(String id) {
        User user = userStorage.getById(Long.valueOf(id));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        return user.getFriends().stream()
                .map(mapper::toUserDto)
                .toList();
    }

    public List<UserDto> getCommonFriends(String id, String otherId) {
        User user = userStorage.getById(Long.valueOf(id));
        User otherUser = userStorage.getById(Long.valueOf(otherId));

        validatorId.validate(user, userStorage.getUsers(), "Пользователь");
        validatorId.validate(otherUser, userStorage.getUsers(), "Пользователь");

        Set<User> userFriends = user.getFriends();
        Set<User> otherUserFriends = otherUser.getFriends();

        userFriends.retainAll(otherUserFriends);

        return userFriends.stream()
                .map(mapper::toUserDto)
                .toList();
    }

    private void validateNameIsNull(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
