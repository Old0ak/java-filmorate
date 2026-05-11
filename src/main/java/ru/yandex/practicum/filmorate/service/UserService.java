package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.List;

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

    private void validateNameIsNull(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
