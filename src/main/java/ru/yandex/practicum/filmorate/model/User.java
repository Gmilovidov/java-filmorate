package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    @NonNull
    private  String email;
    @NonNull
    private  String login;
    private  String name;
    @NonNull
    private LocalDate birthday;

    public User setId(Integer id) {
        this.id = id;
        return this;
    }
}
