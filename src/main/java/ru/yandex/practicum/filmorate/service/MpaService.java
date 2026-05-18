package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaStorage mpaStorage;
    private final MpaMapper mapper;
    private final ValidatorId validatorId = new ValidatorId();

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
}
