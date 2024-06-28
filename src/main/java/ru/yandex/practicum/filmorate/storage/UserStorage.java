package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface UserStorage {
    Map<Integer, User> users = new HashMap<>();

    User addUser(User user) throws ValidationException;

    User modifyUser(User user) throws ValidationException;

    void deleteUser(int userId);

    Collection<User> getAllUsers();

    Set<Integer> getAllUsersId();

    User getUserbyId(int userId);

}
