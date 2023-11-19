import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestTests {
    @Test
    public void firstRequestTest() {
        Response response = RestAssured
            .get("https://playground.learnqa.ru/api/get_text")
            .andReturn();
        response.prettyPrint();
    }

    @Test
    public void requestRestAssuredTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "\u004B\u0075\u0064\u006C\u0061\u0069\u0020\u0045\u0076\u0067\u0065\u006E\u0069\u0069");

        Response response = RestAssured
            .given() // что отправлено в запросе
            .queryParams(params) // параметры, которые передаём из заданной переменной
            .get("https://playground.learnqa.ru/api/hello")
            .andReturn();
        response.prettyPrint();
    }

    @Test
    public void pathJsonTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "\u004B\u0075\u0064\u006C\u0061\u0069\u0020\u0045\u0076\u0067\u0065\u006E\u0069\u0069");

        JsonPath response = RestAssured
            .given() // что отправлено в запросе
            .queryParams(params) // параметры, которые передаём из заданной переменной
            .get("https://playground.learnqa.ru/api/hello")
            .jsonPath();
//        response.prettyPrint(); //вывести полученный ответ

        String answer = response.get("answer");
        //проверка на содержание нужного ключа в json
        if (answer == null) {
            System.out.println("The key 'answer' is absent");
        } else {
            System.out.println(answer);
        }
    }

    @Test
    public void postRestAssuredTest() {
        Map<String, String> bodyForJson = new HashMap<>();
        bodyForJson.put("firstName", "valueFirst");
        bodyForJson.put("secondName", "valueSecond");
        bodyForJson.put("lastName", "valueThird");

        Response response = RestAssured
            .given() // что отправлено в запросе
            .body(bodyForJson)
            .post("https://playground.learnqa.ru/api/check_type")
            .andReturn();
//        response.prettyPrint(); //если оставить так, то в случае ответа в виде текста, будет приведён к XML
        response.print();
    }

    @Test
    public void responseCodeTest() {
        Response twoHundred = RestAssured
            .get("https://playground.learnqa.ru/api/check_type")
            .andReturn();
        int statusCode = twoHundred.getStatusCode();
        System.out.println(statusCode);

        Response fiveHundred = RestAssured
            .get("https://playground.learnqa.ru/api/get_500")
            .andReturn();
        statusCode = fiveHundred.getStatusCode();
        System.out.println(statusCode);

        //default - true
        Response threeHundredThree = RestAssured
            .given()
            .redirects()
            .follow(false) // не идём на переназначение и получаем 303
//            .follow(true)
            .when()
            .get("https://playground.learnqa.ru/api/get_303")
            .andReturn();
        statusCode = threeHundredThree.getStatusCode();
        System.out.println(statusCode);

        Response fourHundredFour = RestAssured
            .get("https://playground.learnqa.ru/api/check_noName")
            .andReturn();
        statusCode = fourHundredFour.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void headersTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeaderFirst", "valueFirst");
        headers.put("myHeaderSecond", "valueSecond");

        Response response = RestAssured
            .given()
            .headers(headers)
            .when()
            .get("https://playground.learnqa.ru/api/show_all_headers")
            .andReturn();
        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        response = RestAssured
            .given()
            .redirects()
            .follow(false) // не идём на переназначение и получаем 303
            .when()
            .get("https://playground.learnqa.ru/api/get_303")
            .andReturn();
        response.prettyPrint();

        String locationHeaders = response.getHeader("Location");
        System.out.println(locationHeaders);
    }

    @Test
    public void getCookiesTest() {
        /**
         * POST This API call set auth cookie by login=secret_login and password=secret_pass
         * Params:
         * @ login : string
         * @ password :
         * https://playground.learnqa.ru/api/get_auth_cookie
         */

        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        //error
//        Map<String, String> dat = new HashMap<>();
//        data.put("login", "secret_loginTwo");
//        data.put("password", "secret_passOne");

        Response response = RestAssured
            .given()
            .body(data)
            .when()
            .post("https://playground.learnqa.ru/api/get_auth_cookie")
            .andReturn();

        System.out.println("\nPretty text");
        response.prettyPrint();

        System.out.println("\nHeaders");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\nCookies");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        // вывести только код аутентификации
//        String responseCookie = response.getCookie("auth_cookie");
//        System.out.println(responseCookie);
    }

    @Test
    public void deliverCookiesTest() {

        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        //test error
//        Map<String, String> data = new HashMap<>();
//        data.put("login", "secret_login2");
//        data.put("password", "secret_pass2");

        Response responsForGet = RestAssured
            .given()
            .body(data)
            .when()
            .post("https://playground.learnqa.ru/api/get_auth_cookie")
            .andReturn();

        String responseCookie = responsForGet.getCookie("auth_cookie");
        Map<String, String> saveCookies = new HashMap<>();

        if (responseCookie != null) {
            saveCookies.put("auth_cookie", responseCookie);
        }

        Response responseForCheck = RestAssured
            .given()
            .body(data)
            .cookies(saveCookies)
            .when()
            .post("https://playground.learnqa.ru/api/check_auth_cookie")
            .andReturn();
        responseForCheck.print();
    }
}
