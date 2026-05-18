package ru.yandex.practicum.filmorate.storage.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.entity.FilmEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {MpaStorageMapper.class, GenreStorageMapper.class})
public interface FilmStorageMapper {

    Film toFilm(FilmEntity filmEntity);

    FilmEntity toFilmEntity(Film film);
}
