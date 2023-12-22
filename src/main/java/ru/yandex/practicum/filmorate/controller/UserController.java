package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public User create(@RequestBody @Valid  User user) {
        log.info("получен запрос на создание пользователя");
       return userService.createUser(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody @Valid  User user) {
        log.info("получен запрос на обновление пользователя");
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("получен запрос на пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUsersById(@PathVariable Long id) {
        log.info("получен запрос на получение пользователя по id");
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос на добавление в друзья");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriendsById(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("получен запрос на удаление из друзей по id");
        userService.deleteFriendById(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getAllFriendsById(@PathVariable Long id) {
        log.info("получен запрос на получение списка друзей");
        return userService.getAllFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getFriendsCommonOtherId(@PathVariable Long id, @PathVariable Long otherId) throws ValidationException {
        log.info("получен запрос на получение списка друзей, общих с другим пользователем");
        return userService.getFriendsCommon(id, otherId);
    }
}
