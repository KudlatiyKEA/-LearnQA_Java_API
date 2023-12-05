package LessonSecondHomeWork;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.Validate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Ex8: Токены
 * Иногда API-метод выполняет такую долгую задачу,
 * что за один HTTP-запрос от него нельзя сразу получить готовый ответ.
 * Это может быть подсчет каких-то сложных вычислений или необходимость собрать информацию по разным источникам.
 *
 * В этом случае на первый запрос API начинает выполнения задачи, а на последующие ЛИБО говорит, что задача еще не готова,
 * ЛИБО выдает результат. Сегодня я предлагаю протестировать такой метод.
 *
 * Сам API-метод находится по следующему URL: https://playground.learnqa.ru/ajax/api/longtime_job
 *
 * Если мы вызываем его БЕЗ GET-параметра token, метод заводит новую задачу, а в ответ выдает нам JSON со следующими полями:
 * * seconds - количество секунд, через сколько задача будет выполнена
 * * token - тот самый токен, по которому можно получить результат выполнения нашей задачи
 *
 * Если же вызвать API-метод, УКАЗАВ GET-параметром token, то мы получим следующий JSON:
 * * error - будет только в случае, если передать token, для которого не создавалась задача.
 * В этом случае в ответе будет следующая надпись - No job linked to this token
 * * status - если задача еще не готова, будет надпись Job is NOT ready, если же готова - будет надпись Job is ready
 * * result - будет только в случае, если задача готова, это поле будет содержать результат
 *
 * Наша задача - написать тест, который сделал бы следующее:
 * 1) создавал задачу
 * 2) делал один запрос с token ДО того, как задача готова, убеждался в правильности поля status
 * 3) ждал нужное количество секунд с помощью функции Thread.sleep() - для этого надо сделать import time
 * 4) делал бы один запрос c token ПОСЛЕ того, как задача готова, убеждался в правильности поля status и наличии поля result
 *
 * Как всегда, код нашей программы выкладываем ссылкой на коммит.
 */
public class ExEighthTests {
    @Test
    public void exEighthTest() throws InterruptedException {

        //создает задачу
        JsonPath response = RestAssured
            .get("https://playground.learnqa.ru/ajax/api/longtime_job")
            .jsonPath();

        String token = response.get("token");
        int time = response.get("seconds");
        //Проверка
        System.out.println("Token:" + token);
        System.out.println("Time to ready:" + time);


        Map<String, String> parameters = new HashMap<>();
        parameters.put("token", token);
        // делает один запрос с token ДО того, как задача готова, убеждается в правильности поля status
        JsonPath response1 = RestAssured
            .given()
            .queryParams(parameters)
            .get("https://playground.learnqa.ru/ajax/api/longtime_job")
            .jsonPath();

        String text = response1.get("status");
        assertEquals("Задача не готова", text);

        // ждет нужное количество секунд с помощью функции time.sleep() - для этого надо сделать import time
        Thread.sleep(time * 1000);

        // делает один запрос c token ПОСЛЕ того, как задача готова, убеждается в правильности поля status и наличии поля result
        JsonPath getResponseToken = RestAssured
            .given()
            .queryParams(parameters)
            .get("https://playground.learnqa.ru/ajax/api/longtime_job")
            .jsonPath();

        String resultant = getResponseToken.get("result");
        String resultStatus = getResponseToken.get("status");

        Validate.notNull(resultant);
        assertEquals("Задача готова", resultStatus);

        System.out.println("Result:" + resultant);
        System.out.println(resultStatus);
    }
}
