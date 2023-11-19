package LessonSecondHomeWork;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

/**
 * Ex7: Долгий редирект
 * Необходимо написать тест, который создает GET-запрос на адрес из предыдущего задания: https://playground.learnqa.ru/api/long_redirect
 * На самом деле этот URL ведет на другой, который мы должны были узнать на предыдущем занятии. Но этот другой URL тоже куда-то редиректит.
 * И так далее. Мы не знаем заранее количество всех редиректов и итоговый адрес.
 * Наша задача — написать цикл, которая будет создавать запросы в цикле, каждый раз читая URL для редиректа из нужного заголовка.
 * И так, пока мы не дойдем до ответа с кодом 200.
 * Ответом должна быть ссылка на тест в вашем репозитории и количество редиректов.
 */

public class ExSeventhTest {
    @Test
    public void redirectTest() {

        Response response = null;
        String endpointGet = "https://playground.learnqa.ru/api/long_redirect"; //стартовое значение
        int statusCode = 0;
        while (statusCode != 200) {
            response = RestAssured
                .given()
                .redirects()
                .follow(false) // не идём на переназначение
                .when()
                .get(endpointGet)
                .andReturn();

            statusCode = response.getStatusCode();
            System.out.println('\n'+statusCode);

            endpointGet = response.getHeader("Location");
            System.out.println(endpointGet+'\n');
        }
    }
}
