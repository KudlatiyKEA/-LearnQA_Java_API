package LessonThirdHomeWork;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ex11: Тест запроса на метод cookie
 * Необходимо написать тест, который делает запрос на метод: https://playground.learnqa.ru/api/homework_cookie
 * Этот метод возвращает какую-то cookie с каким-то значением. Необходимо понять что за cookie и с каким значением, и зафиксировать это поведение с помощью assert.
 * Результатом должна быть ссылка на коммит с тестом.
 */
public class ExeEleventhTests {
    @Test
    public void testHomeworkCookie() {
        Response response = RestAssured
            .get("https://playground.learnqa.ru/api/homework_cookie")
            .andReturn();
//        System.out.println(response.getCookies()); //проверка
        Map<String, String> cookies = response.getCookies();

        assertEquals(200, response.statusCode(),"Unexpected status code");
        assertTrue(cookies.containsKey("HomeWork"), "cookies doesn't contains key 'HomeWork'");
        assertTrue(cookies.containsValue("hw_value"), "cookies doesn't contains value 'hw_value'");
    }
}
