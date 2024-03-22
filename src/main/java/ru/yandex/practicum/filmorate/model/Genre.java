package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class Genre implements Comparable<Genre> {
    private Long id;
    private String name;

    @Override
    public int compareTo(Genre genre) {
        return this.id.compareTo(genre.getId());
    }
}
