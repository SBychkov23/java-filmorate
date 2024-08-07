package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    @Override
    public User addUser(User user) throws ValidationException {
        try {
            userFieldsValidate(user);
            user.setId(getNextId());
            if (Optional.ofNullable(user.getName()).isPresent()) {
                if (user.getName().isBlank())
                    user.setName(user.getLogin());
            } else user.setName(user.getLogin());
            users.put(user.getId(), user);
            log.info("Создан user " + user.getLogin() + " id" + user.getId() + "   " + user);
            return user;
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User modifyUser(User newUser) throws ValidationException {
        // проверяем необходимые условия
        if (Optional.ofNullable(newUser.getId()).isEmpty()) {
            log.error("Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            try {
                userFieldsValidate(newUser);
                oldUser.setName(newUser.getName());
                oldUser.setLogin(newUser.getLogin());
                oldUser.setBirthday(newUser.getBirthday());
                oldUser.setEmail(newUser.getEmail());
                log.info("Обновлен user, id" + oldUser.getId() + "   " + oldUser);
                return oldUser;
            } catch (ValidationException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        log.error("Пользователь с id = " + newUser.getId() + " не найден");
        throw new UserDoesNotExistException(newUser.getId());
    }

    @Override
    public void deleteUser(int userId) {
        if (users.containsKey((userId)))
            users.remove(userId);
        else throw new UserDoesNotExistException(userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUserbyId(int userId) {
        if (users.containsKey((userId)))
            return users.get(userId);
        else throw new UserDoesNotExistException(userId);
    }

    @Override
    public Set<Integer> getAllUsersId() {
        return users.keySet();
    }

    // вспомогательный метод для генерации идентификатора нового фильма
    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++ currentMaxId;
    }

    // вспомогательный метод для валидации данны фильма
    private void userFieldsValidate(User user) throws ValidationException {
        if (! Optional.ofNullable(user.getEmail()).isPresent())
            throw new ValidationException("Поле email не может быть пустым");
        else if (! user.getEmail().contains("@"))
            throw new ValidationException("email должен содержать знак @");
        if (! Optional.ofNullable(user.getLogin()).isPresent())
            throw new ValidationException("login не может быть пустым");
        else if (user.getLogin().contains(" "))
            throw new ValidationException("login не может содержать пробелы");
        if (Optional.ofNullable(user.getBirthday()).isPresent())
            if (user.getBirthday().isAfter(LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())))
                throw new ValidationException("Указана некорректная дата рождения: дата не может быть позже текущего момента времени");
    }

    // служебный метод, возвращающий размер мапы пользователей:
    public int getUsersMapLen() {
        return users.size();
    }
}
