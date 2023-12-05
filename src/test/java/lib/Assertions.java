package lib;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    /**
     * Проверка ответа от сервера на содержание поля с именем name и ожидаемое значение
     * @param response
     * @param name
     * @param expectedValue
     */
    @Step("Assert that json has int key with expected value")
    public static void assertJsonByName(Response response, String name, int expectedValue) {
        response.then().assertThat().body("$", hasKey(name));

        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    @Step("Assert that json has string key with expected value")
    public static void assertJsonByName(Response response, String name, String expectedValue) {
        response.then().assertThat().body("$", hasKey(name));

        String value = response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    /**
     * Проверка, что текст ответа сервера равен ожидаемому
     */
    @Step("Assert that text response equals expected")
    public static void assertResponseTextEquals(Response response, String expectedAnswer) {
        assertEquals(
            expectedAnswer,
            response.asString(),
            "Response text is not expected"
        );
    }

    /**
     * Проверка, что код ответа сервера равен ожидаемому
     */
    @Step("Assert that code response equals expected")
    public static void assertResponseCodeEquals(Response response, int expectedStatusCode) {
        assertEquals(
            expectedStatusCode,
            response.statusCode(),
            "Response status code is not expected"
        );
    }

    /**
     * метод проверяет что в json приходит ответ содержащие конкретное поле
     * @param response
     * @param expectedFieldName
     */
    @Step("Assert that response has expected field")
    public static void assertJsonHasField(Response response, String expectedFieldName) {
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    /**
     * метод проверяет что в json приходит ответ содержащие конкретное поле
     */
    @Step("Assert that response has expected fields")
    public static void assertJsonHasFields(Response response, String[] expectedFieldNames) {
        for (String expectedFieldName : expectedFieldNames) {
            Assertions.assertJsonHasField(response, expectedFieldName);
        }
    }

    /**
     * метод проверяет, что в json нет определенных полей
     */
    public static void assertJsonHasNotField(Response response, String unexpectedFieldName) {
        response.then().assertThat().body("$", not(hasKey(unexpectedFieldName)));
    }
}