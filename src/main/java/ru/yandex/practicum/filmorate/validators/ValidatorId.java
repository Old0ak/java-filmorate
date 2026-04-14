package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exceptions.IdNotExistException;
import ru.yandex.practicum.filmorate.model.Identifiable;

import java.util.Map;

public class ValidatorId implements Validator<Identifiable> {

    @Override
    public void validate(Identifiable entity, Map<Long, ?> collection, String entityType) {
        if (entity == null) {
            throw new IdNotExistException(entityType + " с таким id не найден");
        }
        if (!collection.containsKey(entity.getId())) {
            throw new IdNotExistException(entityType + " с id " + entity.getId() + " не найден");
        }
    }
}
