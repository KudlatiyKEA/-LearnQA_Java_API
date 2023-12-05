package LessonSecondHomeWork;


import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Ex9: Подбор пароля
 * <p>
 * Сегодня к нам пришел наш коллега и сказал, что забыл свой пароль от важного сервиса. Он просит нас помочь ему написать программу, которая подберет его пароль.
 * Условие следующее. Есть метод: https://playground.learnqa.ru/ajax/api/get_secret_password_homework
 * Его необходимо вызывать POST-запросом с двумя параметрами: login и password
 * Если вызвать метод без поля login или указать несуществующий login, метод вернет 500
 * Если login указан и существует, метод вернет нам авторизационную cookie с названием auth_cookie и каким-то значением.
 * У метода существует защита от перебора. Если верно указано поле login, но передан неправильный password,
 * то авторизационная cookie все равно вернется. НО с "неправильным" значением,
 * которое на самом деле не позволит создавать авторизованные запросы.
 * Только если и login, и password указаны верно, вернется cookie с "правильным" значением.
 * Таким образом используя только метод get_secret_password_homework невозможно узнать, передали ли мы верный пароль или нет.
 * По этой причине нам потребуется второй метод, который проверяет правильность нашей авторизованной
 * cookie: https://playground.learnqa.ru/ajax/api/check_auth_cookie
 * Если вызвать его без cookie с именем auth_cookie или с cookie, у которой выставлено "неправильное" значение, метод вернет фразу "You are NOT authorized".
 * Если значение cookie “правильное”, метод вернет: “You are authorized”
 * Коллега говорит, что точно помнит свой login - это значение super_admin
 * А вот пароль забыл, но точно помнит, что выбрал его из списка самых популярных паролей на Википедии (вот тебе и супер админ...).
 * Ссылка: https://en.wikipedia.org/wiki/List_of_the_most_common_passwords
 * Искать его нужно среди списка Top 25 most common passwords by year according to SplashData - список паролей можно скопировать в ваш тест вручную или придумать более хитрый способ, если сможете.
 * Итак, наша задача - написать тест и указать в нем login нашего коллеги и все пароли из Википедии в виде списка. Программа должна делать следующее:
 * 1. Брать очередной пароль и вместе с логином коллеги вызывать первый метод get_secret_password_homework. В ответ метод будет возвращать авторизационную cookie с именем auth_cookie и каким-то значением.
 * 2. Далее эту cookie мы должна передать во второй метод check_auth_cookie. Если в ответ вернулась фраза "You are NOT authorized", значит пароль неправильный. В этом случае берем следующий пароль и все заново. Если же вернулась другая фраза - нужно, чтобы программа вывела верный пароль и эту фразу.
 * Ответом к задаче должен быть верный пароль и ссылка на коммит со скриптом.
 */
@Epic("Homework")
public class ExNinthTests {
    @ParameterizedTest
    @ValueSource(strings = {"123456", "123456789", "qwerty", "password", "1234567", "12345678", "12345", "iloveyou", "111111", "123123", "abc123", "qwerty123", "1q2w3e4r", "admin", "qwertyuiop", "654321", "555555", "lovely", "7777777", "welcome", "888888", "princess", "dragon", "password1", "123qwe"})
    @Description("Девятое задание на получение верного пароля")
    @DisplayName("Test find password")
    public void exNinthTest(String pass) {
        //TODO позже реализовать через Document doc = Jsoup.parse(new URL("https://en.wikipedia.org/wiki/List_of_the_most_common_passwords"), 5000); с циклом
        Map<String, String> data = new HashMap<>();
        data.put("login", "super_admin");
        data.put("password", pass);

        Response response = RestAssured
            .given()
            .params(data)
            .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
            .andReturn();
        response.prettyPrint();
        String auth_cookie = response.getCookie("auth_cookie");
//проверяет правильность нашей авторизованной cookie: https://playground.learnqa.ru/ajax/api/check_auth_cookie
        Response responseCheck = RestAssured
            .given()
            .cookie("auth_cookie", auth_cookie)
            .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
            .andReturn();
        Assertions.assertResponseTextEquals(responseCheck, "You are authorized");
        if(responseCheck.asString().contains("You are authorized")) {
            responseCheck.print();
            System.out.println(data.get("password"));
        }
    }
}
