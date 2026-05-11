package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mpa implements Identifiable {

    @Min(1)
    private long id;

    private String name;

    @Override
    public Long getId() {
        return id;
    }
}
