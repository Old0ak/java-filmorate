package ru.yandex.practicum.filmorate.model.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;
}
