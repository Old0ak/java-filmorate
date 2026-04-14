package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.DateValidationException;
import ru.yandex.practicum.filmorate.exceptions.IdNotExistException;
import ru.yandex.practicum.filmorate.model.response.ErrorResponse;

@RestControllerAdvice
public class ErrorControllerHandler {

    @ExceptionHandler(DateValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(DateValidationException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IdNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(IdNotExistException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
