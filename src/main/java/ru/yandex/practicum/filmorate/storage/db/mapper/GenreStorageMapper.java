package ru.yandex.practicum.filmorate.storage.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.GenreEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreStorageMapper {

    Genre toGenre(GenreEntity genreEntity);

    GenreEntity toGenreEntity(Genre genre);
}
