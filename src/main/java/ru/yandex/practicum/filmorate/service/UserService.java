package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private InMemoryUserStorage userStorage;

    public void addFriend(int userId, int friendId) {
        if (userStorage.getAllUsersId().contains(friendId)) {
            userStorage.getUserbyId(userId).addFriend(userStorage.getUserbyId(friendId));
            userStorage.getUserbyId(friendId).addFriend(userStorage.getUserbyId(userId));
        } else throw new UserDoesNotExistException(friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        if (userStorage.getAllUsersId().contains(friendId)) {
            userStorage.getUserbyId(userId).removeFriend(userStorage.getUserbyId(friendId));
            userStorage.getUserbyId(friendId).removeFriend(userStorage.getUserbyId(userId));
        } else throw new UserDoesNotExistException(friendId);
    }

    public List<User> getAllFriends(int userId) {
        if (userStorage.getAllUsersId().contains(userId))
            return userStorage.getUserbyId(userId).returnFriendsList();
        else throw new UserDoesNotExistException(userId);
    }

    public Set<User> getAllCommonFriends(int userId, int otherUserId) {
        return userStorage.getUserbyId(userId).returnFriendsList().stream()
                .filter(user -> userStorage.getUserbyId(otherUserId).returnFriendsList().contains(user))
                .collect(Collectors.toSet());
    }
}
