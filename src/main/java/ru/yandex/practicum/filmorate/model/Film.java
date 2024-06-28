package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.exceptions.LikerNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "releaseDate"})
@NoArgsConstructor
public class Film {
    Integer id;
    String name;
    String description;
    String releaseDate;
    long duration;
    @Setter(AccessLevel.NONE)
    Integer userLikes = 0;
    @Getter(AccessLevel.NONE)
    Set<User> filmFans = new HashSet();

    public Film(String name, String description, String releaseDate, long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(User user) {
        userLikes++;
        filmFans.add(user);
    }

    public void removeLike(User user) {
        if (filmFans.contains(user)) {
            userLikes--;
            filmFans.remove(user);
        } else throw new LikerNotFoundException(user.getId(), this.id);
    }

    @Override
    public String toString() {
        return String.format("id: %d name: %s  description: %s number of likes: %d ", id, name, description, userLikes);
    }
}
