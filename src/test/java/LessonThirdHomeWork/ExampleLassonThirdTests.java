package LessonThirdHomeWork;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.http.Headers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleLassonThirdTests {
    @Test
    public void assertTrueTest() {
        Response responsForGet = RestAssured
            .get("https://playground.learnqa.ru/api/map")
            .andReturn();
        assertTrue(responsForGet.statusCode() == 200, "Unexpected status code"); //выдаст только true/false без подробностей
    }

    @Test
    public void assertEqualsPassedTest() {
        Response responsForGet = RestAssured
            .get("https://playground.learnqa.ru/api/map")
            .andReturn();
        assertEquals(200, responsForGet.statusCode(), "Unexpected status code"); //выдаст подробную информацию о том, что не так
    }

    //    @Test
//    public void assertEqualsFailedTest (){
//        Response responsForGet = RestAssured
//            .get("https://playground.learnqa.ru/api/map2")
//            .andReturn();
//        assertEquals(200, responsForGet.statusCode(),"Unexpected status code"); //выдаст подробную информацию о том, что не так
//    }
    @Test
    public void assertEqualsFailedTest() {
        Response responsForGet = RestAssured
            .get("https://playground.learnqa.ru/api/map2")
            .andReturn();
        assertEquals(404, responsForGet.statusCode(), "Unexpected status code"); //выдаст подробную информацию о том, что не так
    }
    /**
     * параметризация тестов
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "Helene", "Hans"})
    public void parameterizationTest(String name) {
        Map<String, String> queryParams = new HashMap<>();

        if (name.length() > 0){
            queryParams.put("name", name);
        }

        JsonPath respons = RestAssured
            .given()
            .queryParams(queryParams)
            .get("https://playground.learnqa.ru/api/hello")
            .jsonPath();
        String answer = respons.getString("answer");
        String expectedName = (name.length() > 0) ? name : "indefined"; //Error
//        String expectedName = (name.length() > 0) ? name : "someone";
        assertEquals("Hello, " + expectedName, answer,"The answer is not expected");
    }
}
