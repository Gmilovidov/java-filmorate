package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class FilmValidatorTest {

    private FilmValidator filmValidator;
    private Film film;
    private Boolean valid;

    @BeforeEach
    void beforeEach() {
        filmValidator = new FilmValidator();
        Set<Long> likesTest = new HashSet<>(1);
    }

    @Test
    void shouldReturnFalseForFilmValidations() {
        film = Film.builder()
                .name("name")
                .description("desc")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        valid = filmValidator.checkName(film)
                && filmValidator.checkLength(film)
                && filmValidator.checkDuration(film)
                && filmValidator.checkRelease(film);
        Assertions.assertFalse(valid, "не прошла общая валидация");
    }

    @Test
    void checkNameShouldReturnTrueForVoidName() {
        film = Film.builder()
                .name("")
                .description("desc")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        valid = filmValidator.checkName(film);
        Assertions.assertTrue(valid, "не прошла проверка на пустое имя фильма");
    }

    @Test
    void checkLengthShouldReturnTrueForLengthDescription210() {
        film = Film.builder()
                .name("name")
                .description("deseffsfsegfggfdgdfgdgdfgdfgdfgdgdgdfgdgfsFsfeffgdfghzfjghzkjghzdkjrghkdjrgzdrykrzj" +
                        "gzjkdgrzsggfdfgdgdfgdfggsegsgsegesgsegsgsegesgesgsegsegsegsegsegsegesgsegseg" +
                        "dfgdfgdfgdfgdfgdfgdfgdfgdfgdfghsdhgsfdggdgfggfdgdfgfgdgfrgyzkgc")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        valid = filmValidator.checkLength(film);
        Assertions.assertTrue(valid, "не прошла проверка на длину описания фильма");
    }

    @Test
    void checkReleaseShouldReturnTrueForEarlyReleaseValid() {
        film = Film.builder()
                .name("name")
                .description("desc")
                .releaseDate(LocalDate.of(1600, 1, 1))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        valid = filmValidator.checkRelease(film);
        Assertions.assertTrue(valid, "не проходит проверка на релиз");
    }

    @Test
    void checkDurationShouldReturnTrueForDurationNegative1() {
        film = Film.builder()
                .name("name")
                .description("desc")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(-100)
                .mpa(new Mpa(1, "G"))
                .build();
        valid = filmValidator.checkDuration(film);
        Assertions.assertTrue(valid, "не проходит проверку на длительность");
    }
}
