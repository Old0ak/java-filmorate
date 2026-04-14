package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film implements Identifiable {

    @Min(1)
    private Long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Long duration;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<User> likes = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }
}
