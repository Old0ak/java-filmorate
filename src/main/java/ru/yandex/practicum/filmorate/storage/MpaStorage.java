package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Map;

public interface MpaStorage {

    Mpa getMpa(long id);

    List<Mpa> getAll();

    Map<Long, Mpa> getMpas();
}
