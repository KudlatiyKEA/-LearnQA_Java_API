package LessonSecondHomeWork;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Ex5: Парсинг JSON
 * В рамках этой задачи нужно создать тест, который будет делать GET-запрос на адрес https://playground.learnqa.ru/api/get_json_homework
 * Полученный JSON необходимо распечатать и изучить.
 * Мы увидим, что это данные с сообщениями и временем, когда они были написаны. Наша задача вывести текст второго сообщения.
 * Ответом должна быть ссылка на тест в вашем репозитории.
 */
public class ExFifthTests {
    @Test
    public void parsingJsonTest() {
        JsonPath response = RestAssured
            .given()
            .get("https://playground.learnqa.ru/api/get_json_homework")
            .jsonPath();
        String ansewer = response.getString("messages[1]");
        System.out.println(ansewer);

/**
 * Что получаем в формате json при обращение к endpoint
 * {
 *     "messages": [
 *         {
 *             "message": "This is the first message",
 *             "timestamp": "2021-06-04 16:40:53"
 *         },
 *         {
 *             "message": "And this is a second message",
 *             "timestamp": "2021-06-04 16:41:51"
 *         },
 *         {
 *             "message": "And this is a third message",
 *             "timestamp": "2021-06-04 16:42:22"
 *         }
 *     ]
 * }
 */
    }
}
