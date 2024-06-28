package ru.yandex.practicum.filmorate.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.NoSuchElementException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FilmNotInLikedException extends NoSuchElementException {
    int likerId;

    public FilmNotInLikedException(int likerId, int filmId) {
        super(String.format("Фильм id %d не обнаружен в списке пользователя id %d", filmId, likerId));
        this.likerId = likerId;
    }
}