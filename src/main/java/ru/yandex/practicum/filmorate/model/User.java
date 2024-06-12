package ru.yandex.practicum.filmorate.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = { "email" })
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Integer id;
    String name;
    String email;
    String login;
    LocalDate birthday;

    public User(String username, String email, String login, LocalDate birthday) {
        this.name = username;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
