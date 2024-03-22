package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Genre buildGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("genre_name"))
                .build();
    }

    public Genre getGenre(Long id) {
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject("SELECT * FROM genre WHERE id = ?", this::buildGenre, id);
        } catch (EmptyResultDataAccessException e) {
            String ms = "не найден жанр с id = " + id;
            log.info(ms);
            throw new GenreNotFoundException(ms);
        }
        return genre;
    }

    public List<Genre> getGenreList() {
        return jdbcTemplate.query("SELECT * FROM genre", this::buildGenre).stream()
                .sorted(Comparator.comparingLong(Genre::getId))
                .collect(Collectors.toList());
    }
}
