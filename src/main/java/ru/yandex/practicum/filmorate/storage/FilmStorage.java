package ru.yandex.practicum.filmorate.storage;


import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FilmStorage {
    Map<Integer, Film> films = new HashMap<>();

    Film addFilm(Film film) throws ValidationException;

    Film modifyFilm(Film film) throws ValidationException;

    void deleteFilm(int filmId);

    List<Film> getAllFilms();

    Film getFilmbyId(int filmId);

    Set<Integer> getAllFilmsId();

}
