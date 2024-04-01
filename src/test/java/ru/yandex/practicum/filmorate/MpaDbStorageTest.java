package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class MpaDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private MpaDbStorage mpaDbStorage;
    private final Mpa mpa = new Mpa(1, "G");
    private final List<Mpa> mpaList = List.of(
            new Mpa(1, "G"),
            new Mpa(2, "PG"),
            new Mpa(3, "PG-13"),
            new Mpa(4, "R"),
            new Mpa(5, "NC-17")
    );

    @BeforeEach
    public void beforeEach() {
        mpaDbStorage = new MpaDbStorage(jdbcTemplate);
    }

    @Test
    public void shouldGetMpa() {
        Mpa savedMpa = mpaDbStorage.getMpa(1);

        assertThat(savedMpa)
                .isNotNull()
                .isEqualTo(mpa);
    }

    @Test
    public void shouldGetMpaList() {
        List<Mpa> savedMpaList = mpaDbStorage.getMpaList();

        assertThat(savedMpaList)
                .isNotNull()
                .isEqualTo(mpaList);
    }
}
