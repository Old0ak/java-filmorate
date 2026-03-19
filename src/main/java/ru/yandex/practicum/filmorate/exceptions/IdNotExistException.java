package ru.yandex.practicum.filmorate.exceptions;

public class IdNotExistException extends RuntimeException {
    public IdNotExistException(String message) {
        super(message);
    }
}
