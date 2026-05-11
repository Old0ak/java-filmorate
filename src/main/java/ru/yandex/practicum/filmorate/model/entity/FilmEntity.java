package ru.yandex.practicum.filmorate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmEntity {

    private Long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Long duration;

    private MpaEntity mpa;

    private List<GenreEntity> genres = new ArrayList<>();

    public void addGenre(GenreEntity genreEntity) {
        genres.add(genreEntity);
    }
}
