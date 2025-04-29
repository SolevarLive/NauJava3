package ru.davyd.NauJava.task_8;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Интеграционные тесты для ReportController с использованием RestAssured
 * Тестируют создание отчёта, получение существующего и несуществующего отчётов,
 * а также проверяют поведение при отсутствии аутентификации.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerRestAssuredTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    /**
     * Тестирует успешное создание отчёта
     * Отправляет POST-запрос с Basic-аутентификацией и проверяет,
     * что возвращается id отчёта (не пустое тело и статус 200)
     */
    @Test
    public void testCreateReport() {
        given()
                .auth().preemptive().basic("admin", "admin123")
                .contentType(ContentType.JSON)
                .when()
                .post("/api/reports/create")
                .then()
                .log().all()
                .statusCode(200)
                .body(notNullValue())
                .body(is(not(emptyString())));


    }

    /**
     * Тестирует получение отчёта с несуществующим id.
     * Ожидает статус 404 Not Found.
     */
    @Test
    public void testGetNonExistentReport() {
        given()
                .auth().preemptive().basic("admin", "admin123")
                .when()
                .get("/api/reports/999999")
                .then()
                .log().all()
                .statusCode(404);
    }

    /**
     * Тестирует попытку получить отчёт без аутентификации
     * Ожидается ответ 401 Unauthorized
     */
    @Test
    public void testGetReportWithoutAuth() {
        given()
                .when()
                .get("/api/reports/1")
                .then()
                .log().all()
                .statusCode(401);
    }
}