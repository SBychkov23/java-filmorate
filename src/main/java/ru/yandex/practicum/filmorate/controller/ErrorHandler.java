package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.service.ErrorResponse;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class})
public class ErrorHandler {
    @ExceptionHandler({FilmDoesNotExistException.class,
            UserDoesNotExistException.class,
            LikerNotFoundException.class,
            FilmNotInLikedException.class,
            FriendNotInListException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmOrUserNoExist(final NoSuchElementException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Элемент не найден ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка валидации ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknownError(final Throwable e) {
        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
    }
}
