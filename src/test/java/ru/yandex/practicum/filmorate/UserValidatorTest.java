package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.time.LocalDate;

public class UserValidatorTest {

    private UserValidator userValidator;
    private User user;
    private Boolean valid;

    @BeforeEach
    void beforeEach() {
        userValidator = new UserValidator();
    }

    @Test
    void shouldReturnFalseForUserValidations() {
        user = new User("mail@yandex.ru", "login", "name", LocalDate.now().minusYears(1));
        valid = userValidator.checkMail(user)
                && userValidator.checkLogin(user)
                && userValidator.checkBirthday(user)
                && userValidator.checkNameUser(user);
        Assertions.assertFalse(valid, "не прошла общая валидация");
    }

    @Test
    void checkMailShouldReturnTrueForMailWithoutAT() {
        user = new User("mailyandex.ru", "login", "name", LocalDate.now().minusYears(1));
        valid = userValidator.checkMail(user);
        Assertions.assertTrue(valid, "не прошла проверка на отсутствие AT");
    }

    @Test
    void checkLoginShouldReturnTrueForLoginWithVoid() {
        user = new User("mail@yandex.ru", "log in", "name", LocalDate.now().minusYears(1));
        valid = userValidator.checkLogin(user);
        Assertions.assertTrue(valid, "не прошла проверка на пробел в логине");
    }

    @Test
    void checkBirthdayShouldReturnTrueForBDInFuture() {
        user = new User("mail@yandex.ru", "login", "name", LocalDate.now().plusYears(1));
        valid = userValidator.checkBirthday(user);
        Assertions.assertTrue(valid, "не прошла проверка на день рождения в будущем");
    }

    @Test
    void checkNameUserShouldReturnTrueForNull() {
        user = new User("mail@yandex.ru", "login", null, LocalDate.now().minusYears(1));
        valid = userValidator.checkNameUser(user);
        Assertions.assertTrue(valid, "не прошла проверка с именем null");
    }

    @Test
    void checkNameUserShouldReturnTrueForBlank() {
        user = new User("mail@yandex.ru", "login", "", LocalDate.now().minusYears(1));
        valid = userValidator.checkNameUser(user);
        Assertions.assertTrue(valid, "не прошла проверка с пустым именем");
    }
}
