import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilmControllerTests {
   
   FilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    // тестовый фильм проходящий проверки
    Film testFilm1 = new Film("testName1", "testDesription", "1999-01-01", 100);
    // тестовый фильм не проходящий  проверку по полю имени
    Film testFilm2 = new Film(" ", "testDesription", "1999-01-01", 100);
    // тестовый фильм не проходящий  проверку по полю даты
    Film testFilm3 = new Film("testName3", "testDesription","1599-01-01", 100);
    // тестовый фильм не проходящий  проверку по полю описания
    Film testFilm4 = new Film("testName4", org.apache.commons.lang3.StringUtils.repeat("*", 201), "1999-01-01", 100);
    // тестовый фильм не проходящий  проверку по полю длительности
    Film testFilm5 = new Film("testName5", "testDesription", "1999-01-01", -100);

    @Test
    public void createFilms() throws ValidationException {
        inMemoryFilmStorage.addFilm(testFilm1);
        System.out.println(inMemoryFilmStorage.getAllFilms());
        assertEquals(1, inMemoryFilmStorage.getAllFilms().size());
        try {
            inMemoryFilmStorage.addFilm(testFilm2);
        } catch (Exception e) {
            assertEquals(e.getClass(), ValidationException.class);
        }
    }

    @Test
    public void badDataFillWhileCreate() {
        try {
            inMemoryFilmStorage.addFilm(testFilm2);
        } catch (ValidationException e) {
            assertEquals("Имя не может быть пустым", e.getMessage());
        }
        try {
            inMemoryFilmStorage.addFilm(testFilm3);
        } catch (ValidationException e) {
            assertEquals("Указана некорректная дата релиза фильма", e.getMessage());
        }
        try {
            inMemoryFilmStorage.addFilm(testFilm4);
        } catch (ValidationException e) {
            assertEquals("Превышена длина описания", e.getMessage());
        }
        try {
            inMemoryFilmStorage.addFilm(testFilm5);
        } catch (ValidationException e) {
            assertEquals("Длительность должна быть положительным числом", e.getMessage());
        }
    }

    @Test
    public void badDataFillWhileUpdate() {
        try {
            inMemoryFilmStorage.modifyFilm(testFilm2);
        } catch (Exception e) {
            assertEquals("Id должен быть указан", e.getMessage());
        }
    }
}
