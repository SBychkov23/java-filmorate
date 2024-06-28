package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmDoesNotExistException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    @Override
    public Film addFilm(Film film) throws ValidationException {
        try {
            filmFieldsValidate(film);
            film.setId(getNextId());
            films.put(getNextId(), film);
            log.info("Создан film, id" + film.getId() + "   " + film);
            return film;
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Film modifyFilm(Film newFilm) throws ValidationException {
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
                log.info("Обновлен film, id" + oldFilm.getId() + "   " + oldFilm);
                return oldFilm;
            } catch (ValidationException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        throw new FilmDoesNotExistException(newFilm.getId());
    }

    @Override
    public void deleteFilm(int filmId) {
        if (films.containsKey((filmId)))
            films.remove(filmId);
        else throw new FilmDoesNotExistException(filmId);
    }

    @Override
    public List<Film> getAllFilms() {
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public Film getFilmbyId(int filmId) {
        if (films.containsKey((filmId)))
            return films.get(filmId);
        else throw new FilmDoesNotExistException(filmId);
    }


    @Override
    public Set<Integer> getAllFilmsId() {
        return films.keySet();
    }

    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++ currentMaxId;
    }

    private void filmFieldsValidate(Film film) throws ValidationException {
        if (Optional.ofNullable(film.getDescription()).isPresent())
            if (film.getDescription().length() > 200)
                throw new ValidationException("Превышена длина описания");
        if (! Optional.ofNullable(film.getName()).isPresent())
            throw new ValidationException("Имя не может быть пустым");
        if (Optional.ofNullable(film.getName()).isPresent())
            if (film.getName().isBlank())
                throw new ValidationException("Имя не может быть пустым");
        if (Optional.ofNullable(film.getReleaseDate()).isPresent())
            if (LocalDate.parse(film.getReleaseDate()).isBefore(LocalDate.of(1895, 12, 28)) || (LocalDate.parse(film.getReleaseDate()).isAfter(LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()))))
                throw new ValidationException("Указана некорректная дата релиза фильма");
        if (Optional.ofNullable(film.getDuration()).isPresent())
            if (film.getDuration() <= 0)
                throw new ValidationException("Длительность должна быть положительным числом");
    }

    // служебный метод, возвращающий размер мапы фильмов:
    private int getFilmsMapLen() {
        return films.size();
    }
}
