package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmDoesNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private InMemoryUserStorage userStorage;
    @Autowired
    private InMemoryFilmStorage filmStorage;

    public void addLikeToFilm(int filmId, int userId) {
        if (filmStorage.getAllFilmsId().contains(filmId)) {
            if (userStorage.getAllUsersId().contains(userId)) {
                filmStorage.getFilmbyId(filmId).addLike(userStorage.getUserbyId(userId));
                userStorage.getUserbyId(userId).addLikedFilm(filmStorage.getFilmbyId(filmId));
            } else throw new UserDoesNotExistException(userId);
        } else throw new FilmDoesNotExistException(filmId);
    }

    public void removeLike(int filmId, int userId) {
        if (filmStorage.getAllFilmsId().contains(filmId)) {
            if (userStorage.getAllUsersId().contains(userId)) {
                filmStorage.getFilmbyId(filmId).removeLike(userStorage.getUserbyId(userId));
                System.out.println("aaaa");
                userStorage.getUserbyId(userId).removeLikedFilm(filmStorage.getFilmbyId(filmId));
            } else throw new UserDoesNotExistException(userId);
        } else throw new FilmDoesNotExistException(filmId);
    }

    public List<Film> getTopLikedFilms(int count) {
        List<Film> topLikedFilms = filmStorage.getAllFilms().stream()
                .sorted(Comparator.comparingInt(Film::getUserLikes))
                .collect(Collectors.toList()).reversed();
        if (count < topLikedFilms.size())
            return topLikedFilms.subList(0, count);
        else return topLikedFilms;
    }

}
