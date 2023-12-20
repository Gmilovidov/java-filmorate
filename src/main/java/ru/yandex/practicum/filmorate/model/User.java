package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private  Long id;
    @NonNull
    private  String email;
    @NonNull
    private  String login;
    private  String name;
    @NonNull
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.id = 0L;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void setFriendUser(Long setFriendId) {
        friends.add(setFriendId);
    }
}
