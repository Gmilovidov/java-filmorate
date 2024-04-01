package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Data
@Builder
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
    private Mpa mpa;
    private final Set<Long> likes = new HashSet<>();
    private final TreeSet<Genre> genres = new TreeSet<>();

    public void setLikeFilm(Long userId) {
        likes.add(userId);
    }

    public Map<String, Object> filmToMap(Film film) {
        return Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "id_mpa", mpa.getId()
        );
    }
}

