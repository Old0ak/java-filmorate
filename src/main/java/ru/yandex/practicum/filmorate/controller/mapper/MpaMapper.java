package ru.yandex.practicum.filmorate.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MpaMapper {

    Mpa toMpa(MpaDto mpaDto);

    MpaDto toMpaDto(Mpa mpa);
}
