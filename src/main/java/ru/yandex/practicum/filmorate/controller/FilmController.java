package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping("/films")
    public Film create(@RequestBody @Valid Film film) {
        log.info("Получен запрос на создание фильма");
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    public Film update(@RequestBody @Valid Film film) {
        log.info("Получен запрос на обновление фильма");
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmsById(@PathVariable Long id) {
        log.info("получен запрос на получение фильма по id");
        return filmService.getFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilms(@PathVariable Long id, @PathVariable Long userId) {
        log.info("получен запрос на лайк фильму по id пользователя");
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLikesById(@PathVariable Long id, @PathVariable Long userId) {
        log.info("получен запрос на удаление лайка");
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Long count) {
            return filmService.getPopularFilmsList(count);
    }
}
