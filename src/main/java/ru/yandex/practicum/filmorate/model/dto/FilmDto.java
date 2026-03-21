package ru.yandex.practicum.filmorate.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor
public class FilmDto {

    Long id;

    @NotBlank
    String name;

    @Size(min = 1, max = 200)
    String description;

    @NotNull
    LocalDate releaseDate;

    @Min(1)
    Long duration;
}
