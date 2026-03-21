package ru.yandex.practicum.filmorate.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmMapper {

    Film toFilm(FilmDto filmDto);

    FilmDto toFilmDto(Film film);
}
