package ru.yandex.practicum.filmorate.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.NoSuchElementException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FilmDoesNotExistException extends NoSuchElementException {

    int filmId;

    public FilmDoesNotExistException(int filmId) {
        super("Фильм с id " + filmId + " отсуствует в коллекции");
        this.filmId = filmId;
    }
}
