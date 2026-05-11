package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MpaService {

    private final MpaStorage mpaStorage;
    private final MpaMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

    private final Map<Integer, Mpa> mpas = new HashMap<>();

    public MpaService(MpaStorage mpaStorage, MpaMapper mapper) {
        mpas.put(1, new Mpa(1, "G"));
        mpas.put(2, new Mpa(2, "PG"));
        mpas.put(3, new Mpa(3, "PG-13"));
        mpas.put(4, new Mpa(4, "R"));
        mpas.put(5, new Mpa(5, "NC-17"));
        this.mpaStorage = mpaStorage;
        this.mapper = mapper;
    }

    public List<MpaDto> getAll() {
        return mpaStorage.getAll().stream()
                .map(mapper::toMpaDto)
                .toList();
    }

    public MpaDto getMpa(String id) {
        Mpa mpa = mpaStorage.getMpa(Long.valueOf(id));

        validatorId.validate(mpa, mpaStorage.getMpas(), "Рейтинг MPA");
        return mapper.toMpaDto(mpa);
    }

    public void saveAllMpas() {
        mpas.values().forEach(mpaStorage::save);
    }
}
