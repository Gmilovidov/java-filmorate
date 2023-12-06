package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {

    public boolean checkMail(User user) {
        return !user.getEmail().contains("@");
    }

    public boolean checkLogin(User user) {
        return user.getLogin().contains(" ");
    }

    public boolean checkBirthday(User user) {
        return user.getBirthday().isAfter(LocalDate.now());
    }

    public boolean checkNameUser(User user) {
        return user.getName() == null || user.getName().isBlank();
    }
}
