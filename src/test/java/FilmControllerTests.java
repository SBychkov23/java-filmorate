import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTests {

    FilmController testFilmController = new FilmController();

    // тестовый фильм проходящий проверки
    Film testFilm1 = new Film("testName", "testDesription", "1999-01-01", 100);
    // тестовый фильм проходящий не проверку по полю имени
    Film testFilm2 = new Film(" ", "testDesription", "1999-01-01", 100);
    // тестовый фильм проходящий не проверку по полю даты
    Film testFilm3 = new Film("testName", "testDesription","1999-01-01", 100);
    // тестовый фильм проходящий не проверку по полю описания
    Film testFilm4 = new Film("testName", org.apache.commons.lang3.StringUtils.repeat("*", 201), "1799-01-01", 100);
    // тестовый фильм проходящий не проверку по полю длительности
    Film testFilm5 = new Film("testName", "testDesription", "1999-01-01", -100);

    @Test
    public void creteFilms() throws ValidationException {
        testFilmController.create(testFilm1);
        assertEquals(1, testFilmController.getFilmsMapLen());
        try {
            testFilmController.create(testFilm2);
        } catch (Exception e) {
            assertEquals(e.getClass(), ValidationException.class);
        }
    }

    @Test
    public void badDataFillWhileCreate() {
        try {
            testFilmController.create(testFilm2);
        } catch (ValidationException e) {
            assertEquals("Имя не может быть пустым", e.getMessage());
        }
        try {
            testFilmController.create(testFilm3);
        } catch (ValidationException e) {
            assertEquals("Указана некорректная дата релиза фильма", e.getMessage());
        }
        try {
            testFilmController.create(testFilm4);
        } catch (ValidationException e) {
            assertEquals("Превышена длина описания", e.getMessage());
        }
        try {
            testFilmController.create(testFilm5);
        } catch (ValidationException e) {
            assertEquals("Длительность должна быть положительным числом", e.getMessage());
        }
    }

    @Test
    public void badDataFillWhileUpdate() {
        try {
            testFilmController.update(testFilm2);
        } catch (Exception e) {
            assertEquals("Id должен быть указан", e.getMessage());
        }
    }
}
