package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthTests {
    String cookie;
    String header;
    int userIdOnAuth;

    @BeforeEach //Автовызов кода до запуска
    public void loginUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response response = RestAssured
            .given()
            .body(authData)
            .post("https://playground.learnqa.ru/api/user/login")
            .andReturn();
        this.cookie = response.getCookie("auth_sid");
        this.header = response.getHeader("x-csrf-token");
        this.userIdOnAuth = response.jsonPath().getInt("user_id");
    }

    @Test
    public void userAuthorizationTest() {
        JsonPath responseCheckAuth = RestAssured
            .given()
            .header("x-csrf-token", this.header)
            .cookie("auth_sid", this.cookie)
            .get("https://playground.learnqa.ru/api/user/auth")
            .jsonPath();

        int userIdCheck = responseCheckAuth.getInt("user_id");
        assertTrue(userIdCheck > 0, "Unexpected User id " + userIdCheck);
        assertEquals(userIdOnAuth, userIdCheck, "User id auth request is not  equal to user_id from check request");
    }

    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void userAuthorizationTest(String condition) {

        RequestSpecification spec = RestAssured.given(); // позволяет выполнять запрос, объявляя его по частям
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if (condition.equals("cookie")) {
            spec.cookies("auth_sid", this.cookie);
        } else if (condition.equals("headers")) {
            spec.headers("x-csrf-token", this.header);
        } else {
            throw new IllegalArgumentException("Condition value is known" + condition);
        }

        JsonPath responseForCheck = spec.get().jsonPath();

        assertEquals(0, responseForCheck.getInt("user_id"), "User id should be 0 for unauth request");
    }
}
