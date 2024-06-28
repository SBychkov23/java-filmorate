package ru.yandex.practicum.filmorate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    InMemoryFilmStorage filmStorage;

    @Autowired
    FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film getUserById(@PathVariable String filmId) {
        return filmStorage.getFilmbyId(Integer.valueOf(filmId));
    }


    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) throws ValidationException {
        return filmStorage.modifyFilm(newFilm);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable String id, @PathVariable String userId) {
        filmService.removeLike(Integer.valueOf(id), Integer.valueOf(userId));
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable String id, @PathVariable String userId) {
        filmService.addLikeToFilm(Integer.valueOf(id), Integer.valueOf(userId));
    }

    @GetMapping("/popular")
    public List<Film> getTopLikedFilms(@RequestParam(defaultValue = "10") String count) {
        return filmService.getTopLikedFilms(Integer.valueOf(count));
    }
}