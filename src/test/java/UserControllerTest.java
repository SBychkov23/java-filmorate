import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {

    static UserController testUserController = new UserController();

    //тестовый юзер проходящий проверки
    static User testUser1 = new User("testName", "test@email", "testLogin", LocalDate.of(1999, 01, 01));
    //тестовый юзер не проходящий проверку по написанию email
    static User testUser2 = new User("testName", "testemail", "testLogin", LocalDate.of(1999, 01, 01));
    //тестовый юзер не проходящий проверку по году рождения
    static User testUser3 = new User("testName", "test@email", "testLogin", LocalDate.of(2099, 01, 01));
    //тестовый юзер не проходящий проверку по логину
    static User testUser4 = new User("testName", "test@email", null, LocalDate.of(2009, 01, 01));
    //тестовый юзер не проходящий проверку по логину
    static User testUser5 = new User("testName", "test@email", "test login", LocalDate.of(2009, 01, 01));
    //тестовый юзер не проходящий проверку по написанию email
    static User testUser6 = new User("testName", null, "testlogin", LocalDate.of(2009, 01, 01));


    @Test
    public void usersCreate() throws ValidationException {
        testUserController.create(testUser1);
        try {
            testUserController.create(testUser2);
        } catch (Exception e) {
            assertEquals(e.getClass(), ValidationException.class);
        }
        assertEquals(1, testUserController.getUsersMapLen());
    }

    @Test
    public void badDataFillWhileCreate() {
        try {
            testUserController.create(testUser2);
        } catch (Exception e) {
            assertEquals("email должен содержать знак @", e.getMessage());
        }
        try {
            testUserController.create(testUser3);
        } catch (Exception e) {
            assertEquals("Указана некорректная дата рождения: дата не может быть позже текущего момента времени", e.getMessage());
        }
        try {
            testUserController.create(testUser4);
        } catch (Exception e) {
            assertEquals("Имя login может быть пустым", e.getMessage());
        }
        try {
            testUserController.create(testUser5);
        } catch (Exception e) {
            assertEquals("login не может содержать пробелы", e.getMessage());
        }
        try {
            testUserController.create(testUser6);
        } catch (Exception e) {
            assertEquals("Поле email не может быть пустым", e.getMessage());
        }
    }

    @Test
    public void badDataFillWhileUpdate() {
        try {
            testUserController.update(testUser2);
        } catch (Exception e) {
            assertEquals("Id должен быть указан", e.getMessage());
        }
    }
}