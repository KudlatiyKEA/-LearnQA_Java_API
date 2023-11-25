package LessonThirdHomeWork;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleLassonNegativePositiveTests {
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void userAuthorizationTest(String condition) {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
            .given()
            .body(authData)
            .post("https://playground.learnqa.ru/api/user/login")
            .andReturn();

        Map<String, String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();

        RequestSpecification spec = RestAssured.given(); // позволяет выполнять запрос, объявляя его по частям
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if (condition.equals("cookie")) {
            spec.cookies("auth_sid", cookies.get("auth_sid"));
        } else if (condition.equals("headers")) {
            spec.headers("x-csrf-token", headers.get("x-csrf-token"));
        } else {
            throw new IllegalArgumentException("Condition value is known" + condition);
        }

        JsonPath responseForCheck = spec.get().jsonPath();

        assertEquals(0, responseForCheck.getInt("user_id"), "User id should be 0 for unauth request");
    }
}
