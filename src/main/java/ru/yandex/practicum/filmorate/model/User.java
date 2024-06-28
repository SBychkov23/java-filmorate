package ru.yandex.practicum.filmorate.model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.exceptions.FilmNotInLikedException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "email"})
@NoArgsConstructor
public class User {
    Integer id;
    String name;
    String email;
    String login;
    LocalDate birthday;
    @Getter(AccessLevel.NONE)
    Set<Film> likedFilms = new HashSet();
    @Getter(AccessLevel.NONE)
    Set<User> friends = new HashSet();

    public User(String username, String email, String login, LocalDate birthday) {
        this.name = username;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public void addLikedFilm(Film film) {
        likedFilms.add(film);
    }

    public void removeLikedFilm(Film film) {
        if (likedFilms.contains(film))
            likedFilms.remove(film);
        else throw new FilmNotInLikedException(this.id, film.getId());
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);

    }

    @Override
    public String toString() {
        return String.format("id: %d name: %s  login: %s email: %s number of friends: %d number of liked films: %d", id, name, login, email, friends.size(), likedFilms.size());
    }

    public List<User> returnFriendsList() {
        return friends.stream().toList();
    }


}
