package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.time.LocalDate;

@SpringBootTest
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
        user = User.builder()
                .email("user@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1993, 6,20))
                .name("name")
                .build();
        valid = userValidator.checkMail(user)
                && userValidator.checkLogin(user)
                && userValidator.checkBirthday(user)
                && userValidator.checkNameUser(user);
        Assertions.assertFalse(valid, "не прошла общая валидация");
    }

    @Test
    void checkMailShouldReturnTrueForMailWithoutAT() {
        user = User.builder()
                .email("usermail.ru")
                .login("login")
                .birthday(LocalDate.of(1993, 6,20))
                .name("name")
                .build();
        valid = userValidator.checkMail(user);
        Assertions.assertTrue(valid, "не прошла проверка на отсутствие AT");
    }

    @Test
    void checkLoginShouldReturnTrueForLoginWithVoid() {
        user = User.builder()
                .email("user@mail.ru")
                .login("log  in")
                .birthday(LocalDate.of(1993, 6,20))
                .name("name")
                .build();
        valid = userValidator.checkLogin(user);
        Assertions.assertTrue(valid, "не прошла проверка на пробел в логине");
    }

    @Test
    void checkBirthdayShouldReturnTrueForBDInFuture() {
        user = User.builder()
                .email("user@mail.ru")
                .login("login")
                .birthday(LocalDate.of(2200, 6,20))
                .name("name")
                .build();
        valid = userValidator.checkBirthday(user);
        Assertions.assertTrue(valid, "не прошла проверка на день рождения в будущем");
    }

    @Test
    void checkNameUserShouldReturnTrueForNull() {
        user = User.builder()
                .email("user@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1993, 6,20))
                .name(null)
                .build();
        valid = userValidator.checkNameUser(user);
        Assertions.assertTrue(valid, "не прошла проверка с именем null");
    }

    @Test
    void checkNameUserShouldReturnTrueForBlank() {
        user = User.builder()
                .email("user@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1993, 6,20))
                .name("")
                .build();
        valid = userValidator.checkNameUser(user);
        Assertions.assertTrue(valid, "не прошла проверка с пустым именем");
    }
}
