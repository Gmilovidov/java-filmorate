package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    private final List<User> users = new ArrayList<>();
    private final UserValidator userValidator = new UserValidator();
    private int count = 0;

    @PostMapping("/users")
    public User create(@RequestBody @Valid  User user) {
        log.info("получен запрос на создание пользователя");
        if (userValidator.checkNameUser(user)) {
            user.setName(user.getLogin());
        }
            checkerValidUser(user);
            user.setId(generateIdUser());
            users.add(user);
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody @Valid  User user) {
        log.info("получен запрос на обновление пользователя");
        if (userValidator.checkNameUser(user)) {
            user.setName(user.getLogin());
        }
        try {
            for (User u : users) {
                if (u.getId() == user.getId()) {
                    break;
                } else {
                    throw new ValidationException("пользователь не найден");
                }
            }
            checkerValidUser(user);
            users.removeIf(u -> u.getId() == user.getId());
            users.add(user);
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("получен запрос на пользователей");
        return users;
    }

    private void checkerValidUser(User user) {
        try {
            if (userValidator.checkMail(user)) {
                throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
            }
            if (userValidator.checkLogin(user)) {
                throw new ValidationException("логин не может быть пустым и содержать пробелы");
            }
            if (userValidator.checkBirthday(user)) {
                throw new ValidationException("дата рождения не может быть в будущем");
            }
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    private int generateIdUser() {
        return ++count;
    }
}
