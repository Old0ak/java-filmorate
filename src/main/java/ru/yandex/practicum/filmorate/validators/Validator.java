package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.Identifiable;

import java.util.Map;

public interface Validator<T extends Identifiable> {
    void validate(T entity, Map<Long, ?> collection, String entityType);
}
