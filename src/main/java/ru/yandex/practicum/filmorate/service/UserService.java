package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User createUser(User user) {
        validateNameIsNull(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validateNameIsNull(user);
        return userStorage.updateUser(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getUser(String id) {
        return userStorage.getById(Long.valueOf(id));
    }

    public void addFriend(String id, String friendId) {
        User user = userStorage.getById(Long.valueOf(id));
        User friend = userStorage.getById(Long.valueOf(friendId));

        user.getFriends().add(friend);
        friend.getFriends().add(user);
    }

    public void removeFriend(String id, String friendId) {
        User user = userStorage.getById(Long.valueOf(id));
        User friend = userStorage.getById(Long.valueOf(friendId));

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
    }

    public List<User> getUserFriends(String id) {
        User user = userStorage.getById(Long.valueOf(id));

        return user.getFriends().stream().toList();
    }

    public List<User> getCommonFriends(String id, String otherId) {
        User user = userStorage.getById(Long.valueOf(id));
        User otherUser = userStorage.getById(Long.valueOf(otherId));

        Set<User> userFriends = user.getFriends();
        Set<User> otherUserFriends = otherUser.getFriends();

        userFriends.retainAll(otherUserFriends);

        return new ArrayList<>(userFriends);
    }

    private void validateNameIsNull(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
