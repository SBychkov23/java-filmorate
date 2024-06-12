package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    Integer id;
    String name;
    String description;
    String releaseDate;
    long duration;

    public Film(String name, String description, String releaseDate, long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
