package LessonThirdHomeWork;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ex10: Тест на короткую фразу
 * В рамках этой задачи с помощью JUnit необходимо написать тест,
 * который проверяет длину какое-то переменной типа String с помощью любого выбранного Вами метода assert.
 * Если текст длиннее 15 символов, то тест должен проходить успешно. Иначе падать с ошибкой.
 * Результатом должна стать ссылка на такой тест.
 */
public class ExTenTest {

    @ParameterizedTest
    @ValueSource(strings = {"12354", "12345678901234567890123456789", "12345678901234567890", "12345678901234"})
    public void parameterizationTest(String data) {
        assertTrue(data.length() > 15, "Very short line");
    }
}
