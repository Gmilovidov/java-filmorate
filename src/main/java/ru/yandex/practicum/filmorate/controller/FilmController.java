package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class FilmController {

    private final List<Film> films = new ArrayList<>();
    private final FilmValidator filmValidator = new FilmValidator();
    private int count = 0;

    @PostMapping("/films")
    public Film create(@RequestBody @Valid Film film) {
        log.info("Получен запрос на создание фильма");
        try {
            if (filmValidator.checkName(film)) {
                throw new ValidationException("Название не может быть пустым");
            }
            if (filmValidator.checkLength(film)) {
                throw new ValidationException("максимальная длина описания — 200 символов");
            }
            if (filmValidator.checkRelease(film)) {
                throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
            }
            if (filmValidator.checkDuration(film)) {
                throw new ValidationException("продолжительность фильма должна быть положительной");
            }
            film.setId(generateIdFilm());
            films.add(film);
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody @Valid Film film) {
        log.info("Получен запрос на обновление фильма");
        try {
            for (Film f : films) {
                if (f.getId() == film.getId()) {
                    break;
                } else {
                    throw new ValidationException("фильм не найден");
                }
            }
            if (filmValidator.checkName(film)) {
                throw new ValidationException("Название фильма не может быть пустым");
            }
            if (filmValidator.checkLength(film)) {
                throw new ValidationException("максимальная длина описания — 200 символов");
            }
            if (filmValidator.checkRelease(film)) {
                throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
            }
            if (filmValidator.checkDuration(film)) {
                throw new ValidationException("продолжительность фильма должна быть положительной");
            }
            films.removeIf(f -> f.getId() == film.getId());
            films.add(film);
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос на фильмы");
        return films;
    }

    public int generateIdFilm() {
        return ++count;
    }
}
