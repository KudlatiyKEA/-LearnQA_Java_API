package LessonThirdHomeWork;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ex12: Тест запроса на метод header
 * Необходимо написать тест, который делает запрос на метод: https://playground.learnqa.ru/api/homework_header
 * Этот метод возвращает headers с каким-то значением. Необходимо понять что за headers и с каким значением, и зафиксировать это поведение с помощью assert
 * Результатом должна быть ссылка на коммит с тестом.
 */
public class ExTwelfthTests {
    @Test
    public void exTwelfthTest() {
        Response response = RestAssured
            .get("https://playground.learnqa.ru/api/homework_header")
            .andReturn();
        //что получаем
//        Headers responseHeaders = response.getHeaders();
//        System.out.println(responseHeaders);
        String responseHeader = response.getHeader("x-secret-homework-header");
        assertEquals("Some secret value", responseHeader, "Unexpected header value");
    }
}
