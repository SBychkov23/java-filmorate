package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(of = { "email" })
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String email;
    private String login;
    private LocalDate birthday;
    public User(String username, String email, String login, LocalDate birthday) {
        this.username = username;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
