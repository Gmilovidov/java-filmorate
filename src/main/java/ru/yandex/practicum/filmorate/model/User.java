package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    private  int id;
    @NonNull
    private  String email;
    @NonNull
    private  String login;
    private  String name;
    @NonNull
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.id = 0;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
