package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {

    private static final LocalDate releaseValid = LocalDate
            .of(1895, 12, 28);

    public boolean checkName(Film film) {
        return film.getName().isBlank();
    }

    public boolean checkLength(Film film) {
        return film.getDescription().length() > 200;
    }

    public boolean checkRelease(Film film) {
        return film.getReleaseDate().isBefore(releaseValid);
    }

    public boolean checkDuration(Film film) {
        return film.getDuration() < 0;
    }

}
