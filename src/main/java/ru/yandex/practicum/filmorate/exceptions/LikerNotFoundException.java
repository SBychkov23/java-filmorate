package ru.yandex.practicum.filmorate.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.NoSuchElementException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LikerNotFoundException extends NoSuchElementException {
    int likerId;

    public LikerNotFoundException(int likerId, int filmId) {
        super(String.format("Пользователь id %d не обнаружен в списке оценивших фильм id %d", likerId, filmId));
        this.likerId = likerId;
    }
}
