package ru.yandex.practicum.filmorate.storage.user.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.IdExistValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private final IdExistValidator idExistValidator = new IdExistValidator();

    private Long generatedId = 0L;

    @Override
    public User createUser(User user) {
        user.setId(++generatedId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        idExistValidator.validate(user, users, "Пользователь");
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(Long id) {
        idExistValidator.validate(users.get(id), users, "Пользователь");
        return users.get(id);
    }
}
