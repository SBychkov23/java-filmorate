package ru.yandex.practicum.filmorate.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.NoSuchElementException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FriendNotInListException extends NoSuchElementException {
    int userId;
    int friendId;

    public FriendNotInListException(int userId, int friendId) {
        super(String.format("Пользователь id %d не найден в списке друзей пользователя id %d", userId, friendId));
        this.userId = userId;
        this.friendId = friendId;
    }
}
