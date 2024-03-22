package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmById(Long id);

    void likeFilm(Long id, Long userId);

    void deleteLike(Long id, Long userId);

    List<Film> getPopularFilmsList(Long count);

}
