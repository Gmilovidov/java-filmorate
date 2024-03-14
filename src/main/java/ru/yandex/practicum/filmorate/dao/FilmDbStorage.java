package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public List<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film getFilmById(Long id) {
        return null;
    }

    @Override
    public void likeFilm(Long id, Long userId) {

    }

    @Override
    public void deleteLike(Long id, Long userId) {

    }

    @Override
    public List<Film> getPopularFilmsList(Long count) {
        return null;
    }
}
