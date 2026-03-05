package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(DateValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleDateValidationException(DateValidationException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return response;
    }

    @ExceptionHandler(IdNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> handleIdNoxExistException(IdNotExistException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return response;
    }
}
