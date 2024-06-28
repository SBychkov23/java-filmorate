package ru.yandex.practicum.filmorate.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.NoSuchElementException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserDoesNotExistException extends NoSuchElementException {

    int userId;

    public UserDoesNotExistException(int userId) {
        super("Пользователь не обнаружен");
        this.userId = userId;
    }
}
