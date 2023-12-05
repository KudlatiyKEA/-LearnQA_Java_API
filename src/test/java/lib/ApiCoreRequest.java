package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequest {
    @Step("Make a DELETE-request with token and auth cookie")
    public Response makeDeleteRequest(String url, String token, String cookie) {
        return given()
            .filter(new AllureRestAssured())
            .log().all()
            .header(new Header("x-csrf-token",token))
            .cookie("auth_sid",cookie)
            .delete(url)
            .andReturn();
    }

    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
            .filter(new AllureRestAssured())
            .log().all()
            .header(new Header("x-csrf-token",token))
            .cookie("auth_sid",cookie)
            .get(url)
            .andReturn();
    }

    @Step("Make a GET-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie) {
        return given()
            .filter(new AllureRestAssured())
            .cookie("auth_sid",cookie)
            .get(url)
            .andReturn();
    }

    @Step("Make a GET-request with token only")
    public Response makeGetRequestWithToken(String url, String token) {
        return given()
            .filter(new AllureRestAssured())
            .header(new Header("x-csrf-token",token))
            .get(url)
            .andReturn();
    }

    @Step("Make a POST-request")
    public Response makePostRequest(String url, Map<String,String> authData) {
        return given()
            .filter(new AllureRestAssured())
            .log().all()
            .body(authData)
            .post(url)
            .andReturn();
    }

    @Step("Make GET-request without parameters")
    public Response makeGetRequest(String url) {
        return  given()
            .filter(new AllureRestAssured())
            .get(url)
            .andReturn();
    }

    @Step("Make a PUT-request")
    public Response makePutRequest(String url, String token, String cookie, Map<String,String> editData) {
        return  given()
            .filter(new AllureRestAssured())
            .log().all()
            .header(new Header("x-csrf-token",token))
            .cookie("auth_sid", cookie)
            .body(editData)
            .put(url)
            .andReturn();
    }

    @Step("Make a PUT-request without token and cookie")
    public Response makePutRequest(String url, Map<String,String> editData) {
        return  given()
            .filter(new AllureRestAssured())
            .body(editData)
            .put(url)
            .andReturn();
    }
}