package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private int duration;
    private  Set<Long> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration, Set<Long> likes) {
        this.id = 0L;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void setLikeFilm(Long setUserId) {
        likes.add(setUserId);
    }
}
