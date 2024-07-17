package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private InMemoryUserStorage userStorage;

    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userStorage.getUserbyId(Integer.valueOf(userId));
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        return userStorage.addUser(user);
    }

    @PutMapping
    public User update(@RequestBody User newUser) throws ValidationException {
        return userStorage.modifyUser(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.addFriend(Integer.valueOf(id), Integer.valueOf(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.deleteFriend(Integer.valueOf(id), Integer.valueOf(friendId));
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable String id) {
        return userService.getAllFriends(Integer.valueOf(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getAllCommonFriends(Integer.valueOf(id), Integer.valueOf(otherId));
    }


}