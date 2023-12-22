package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final FilmValidator filmValidator = new FilmValidator();

    @Override
    public Film createFilm(Film film) {
        checkerValidFilm(film);
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        checkerValidFilm(film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    @Override
    public void likeFilm(Long id, Long userId) {
        filmStorage.likeFilm(id, userId);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        filmStorage.deleteLike(id, userId);
    }

    @Override
    public List<Film> getPopularFilmsList(Long count) {
        return filmStorage.getPopularFilmsList(count);
    }

    private void checkerValidFilm(Film film) {
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
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
