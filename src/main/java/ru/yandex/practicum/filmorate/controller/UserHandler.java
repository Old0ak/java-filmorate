package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.IdExistValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserHandler {

    private final Map<Long, User> users = new HashMap<>();
    private final IdExistValidator idExistValidator = new IdExistValidator();

    private Long generatedId = 0L;

    public User createUser(@Valid User user) {
        validateNameIsNull(user);
        user.setId(++generatedId);
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(@Valid User user) {
        idExistValidator.validate(user, users, "Пользователь");
        validateNameIsNull(user);
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    private void validateNameIsNull(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
