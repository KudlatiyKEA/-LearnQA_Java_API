package LessonSecondHomeWork;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


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
        response.prettyPrint();

        String locationHeaders = response.getHeader("Location");
        System.out.println(locationHeaders);
    }
}
