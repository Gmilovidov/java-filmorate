package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.time.LocalDate;

@SpringBootTest
public class FilmValidatorTest {

    private FilmValidator filmValidator;
    private Film film;
    private Boolean valid;

    @BeforeEach
    void beforeEach() {
        filmValidator = new FilmValidator();
    }

    @Test
    void shouldReturnFalseForFilmValidations() {
        film = new Film("name", "descript", LocalDate.now().minusDays(1), 100);
        valid = filmValidator.checkName(film)
                && filmValidator.checkLength(film)
                && filmValidator.checkDuration(film)
                && filmValidator.checkRelease(film);
        Assertions.assertFalse(valid, "не прошла общая валидация");
    }

    @Test
    void checkNameShouldReturnTrueForVoidName() {
        film = new Film("", "descript", LocalDate.now().minusDays(1), 100);
        valid = filmValidator.checkName(film);
        Assertions.assertTrue(valid, "не прошла проверка на пустое имя фильма");
    }

    @Test
    void checkLengthShouldReturnTrueForLengthDescription210() {
        film = new Film("name", "descriptttdescriptttdescriptttdescriptttdescriptttdes" +
                "criptttdescriptttdescriptttdescriptttdescriptttdescriptttdescriptttdescr" +
                "iptttdescriptttdescriptttdescriptttdescriptttdescriptttdescripttt" +
                "descriptttdescripttt", LocalDate.now().minusDays(1),100);
        valid = filmValidator.checkLength(film);
        Assertions.assertTrue(valid, "не прошла проверка на длину описания фильма");
    }

    @Test
    void checkReleaseShouldReturnTrueForEarlyReleaseValid() {
        film = new Film("name", "descript", LocalDate.now().minusYears(140), 100);
        valid = filmValidator.checkRelease(film);
        Assertions.assertTrue(valid, "не проходит проверка на релиз");
    }

    @Test
    void checkDurationShouldReturnTrueForDurationNegative1() {
        film = new Film("name", "descript", LocalDate.now().minusDays(1), -1);
        valid = filmValidator.checkDuration(film);
        Assertions.assertTrue(valid, "не проходит проверку на длительность");
    }
}
