package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        try {
            filmFieldsValidate(film);
            film.setId(getNextId());
            films.put(film.getId(), film);
            log.info("Создан film, id"+film.getId()+"   "+ film);
            return film;
        }
        catch (ValidationException e){
            throw e;
        }
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) throws ValidationException {
        // проверяем необходимые условия
        if (Optional.ofNullable(newFilm.getId()).isEmpty()) {
            throw new ValidationException("Id должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            try {
                filmFieldsValidate(newFilm);
                oldFilm.setDescription(newFilm.getDescription());
                oldFilm.setName(newFilm.getName());
                oldFilm.setDuration(newFilm.getDuration());
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
                log.info("Обновлен film, id"+oldFilm.getId()+"   "+ oldFilm);
                return oldFilm;
            }
            catch (ValidationException e){
                throw e;
            }
        }
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }
    // вспомогательный метод для генерации идентификатора нового фильма
    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
    // вспомогательный метод для валидации данны фильма
    private void filmFieldsValidate(Film film) throws ValidationException {
        if (Optional.ofNullable(film.getDescription()).isPresent())
                if(film.getDescription().length()>200)
                    throw new ValidationException("Превышена длина описания");
        if (!Optional.ofNullable(film.getName()).isPresent())
                throw new ValidationException("Имя не может быть пустым");
        if (Optional.ofNullable(film.getName()).isPresent())
            if(film.getName().isBlank())
                throw new ValidationException("Имя не может быть пустым");
        if (Optional.ofNullable(film.getReleaseDate()).isPresent())
            if (film.getReleaseDate().isBefore(LocalDate.of(1885, 12, 28))||film.getReleaseDate().isAfter(LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())))
            throw new ValidationException("Указана некорректная дата релиза фильма");
        if (!Optional.ofNullable(film.getDuration()).isPresent())
            if (film.getDuration().isNegative())
                throw new ValidationException("Длительность должна быть положительным числом");

    }
    // служебный метод, возвращающий размер мапы фильмов:
    public int getFilmsMapLen() {
        return films.size();
    }
}