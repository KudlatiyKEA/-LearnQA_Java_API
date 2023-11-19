package LessonSecondHomeWork;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

/**
 * Ex6: Редирект
 * Необходимо написать тест, который создает GET-запрос на адрес: https://playground.learnqa.ru/api/long_redirect
 * С этого адреса должен происходит редирект на другой адрес. Наша задача — распечатать адрес, на который редиректит указанные URL.
 * Ответом должна быть ссылка на тест в вашем репозитории.
 */

public class ExSixthTest {
    @Test
    public void redirectTest() {

        Response response = RestAssured
            .given()
            .redirects()
            .follow(false) // не идём на переназначение
            .when()
            .get("https://playground.learnqa.ru/api/long_redirect")
            .andReturn();
//        response.prettyPrint();

        String locationHeaders = response.getHeader("Location");
        System.out.println(locationHeaders);
    }
}
