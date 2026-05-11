package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

    void addFriend(Long userId, Long friendId);

    List<User> getFriends(Long id);

    void removeFriend(Long userId, Long friendId);

    List<User> getCommonFriends(Long userId, Long friendId);
}
