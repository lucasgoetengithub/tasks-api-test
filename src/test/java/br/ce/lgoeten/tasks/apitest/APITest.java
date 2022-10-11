package br.ce.lgoeten.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured
                .given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test
    public void deveAdicionarTarefasComSucesso() {
        RestAssured
                .given()
                    .body("{\"task\": \"Study\", \"dueDate\": \"2022-12-10\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(201)
                .log().all();
    }

    @Test
    public void naoDeveAdicionarTarefasComSucesso() {
        RestAssured
                .given()
                .body("{\"task\": \"Study\", \"dueDate\": \"2012-12-10\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
                .log().all();
    }


}
