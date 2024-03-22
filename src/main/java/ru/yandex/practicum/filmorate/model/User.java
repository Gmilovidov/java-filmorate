package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class User {
    private Long id;
    @NonNull
    private  String email;
    @NonNull
    private  String login;
    private  String name;
    @NonNull
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();

    public void setFriendUser(Long friendId) {
        friends.add(friendId);
    }

    public Map<String, Object> userToMap(User user) {
        return Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        );
    }
}
