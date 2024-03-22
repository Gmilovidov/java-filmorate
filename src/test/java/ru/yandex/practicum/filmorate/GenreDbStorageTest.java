package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class GenreDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private GenreDbStorage genreDbStorage;
    private final Genre genre = new Genre(1L, "Комедия");
    private final List<Genre> genreListTest = List.of(
            new Genre(1L, "Комедия"),
            new Genre(2L, "Драма"),
            new Genre(3L, "Мультфильм"),
            new Genre(4L, "Триллер"),
            new Genre(5L, "Документальный"),
            new Genre(6L, "Боевик")
    );

    @BeforeEach
    public void beforeEach() {
        genreDbStorage = new GenreDbStorage(jdbcTemplate);
    }

    @Test
    public void shouldGetGenres() {
        List<Genre> savedGenres = genreDbStorage.getGenreList().stream()
                .sorted(Comparator.comparingLong(Genre::getId))
                .collect(Collectors.toList());

        assertThat(savedGenres)
                .isNotNull()
                .isEqualTo(genreListTest);
    }
}
