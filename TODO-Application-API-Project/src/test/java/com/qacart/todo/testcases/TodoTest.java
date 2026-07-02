package com.qacart.todo.testcases;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TodoTest {


    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhNDVhNzJkNmUzYmYzMDAxNTI5OTA1ZiIsImZpcnN0TmFtZSI6Ik5vdXIiLCJsYXN0TmFtZSI6IkFobWVkIiwiaWF0IjoxNzgyOTQ5NzI3fQ.wpSYV9roSFFl1pofF7BFiBy6mJtcghKpQXFlv_wsWaQ";

    @Test
    public void shouldBeAbleToCreateTodo() {
        String body = "{\n" +
                "  \"isCompleted\": \"false\",\n" +
                "  \"item\": \"Learn Appium\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("isCompleted", equalTo(false))
                .assertThat().body("item", equalTo("Learn Appium"));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutAuth() {
        String body = "{\n" +
                "  \"isCompleted\": \"false\",\n" +
                "  \"item\": \"Learn Appium\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("Unauthorized, please insert a correct token"));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutItem() {
        String body = "{\n" +
                "  \"isCompleted\": \"false\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("\"item\" is required"));
    }

    @Test
    public void shouldnotbeabletocreatetodowithoutiscompleted() {
        String body = "{\n" +
                "  \"item\": \"Learn Appium\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("\"isCompleted\" is required"));
    }

    @Test
    public void getTodoById() {
        String taskId = "6a45a7716e3bf30015299062";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get("/api/v1/tasks/" +taskId)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("_id", equalTo(taskId));


    }

    @Test
    public void getAllTodos() {
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get("/api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void deleteTodoById() {
        String taskId = "6a45a7a46e3bf30015299065";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .delete("/api/v1/tasks/" +taskId)
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

}

