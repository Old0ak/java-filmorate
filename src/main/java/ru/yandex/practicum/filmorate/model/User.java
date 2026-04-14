package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Identifiable {

    @Min(1)
    private long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<User> friends = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }
}
