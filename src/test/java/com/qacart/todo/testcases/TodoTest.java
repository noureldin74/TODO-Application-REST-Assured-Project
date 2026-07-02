package com.qacart.todo.testcases;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TodoTest {



    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhNDVhNzJkNmUzYmYzMDAxNTI5OTA1ZiIsImZpcnN0TmFtZSI6Ik5vdXIiLCJsYXN0TmFtZSI6IkFobWVkIiwiaWF0IjoxNzgyOTQ5NzI3fQ.wpSYV9roSFFl1pofF7BFiBy6mJtcghKpQXFlv_wsWaQ";

    @Test
    public void shouldBeAbleToCreateTodo() {

        Todo todo = new Todo(false, "Learn Appium");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("isCompleted"), equalTo(false));
        assertThat(response.path("item"), equalTo("Learn Appium"));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutAuth() {
        Todo todo = new Todo(false, "Learn Appium");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all().extract().response();
        assertThat(response.statusCode(), equalTo(401));
        assertThat(response.path("message"), equalTo("Unauthorized, please insert a correct token"));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutItem() {
        Todo todo = new Todo(false, null);

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("\"item\" is required"));
    }

    @Test
    public void shouldnotbeabletocreatetodowithoutiscompleted() {
        Todo todo = new Todo(null, "Learn Appium");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("\"isCompleted\" is required"));
    }

    @Test
    public void getTodoById() {
        String taskId = "6a45b3dd6e3bf30015299167";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get("/api/v1/tasks/" +taskId)
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("_id"), equalTo(taskId));

    }

    @Test
    public void getAllTodos() {
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void deleteTodoById() {
        String taskId = "6a46a3b66e3bf30015299fe1";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .delete("/api/v1/tasks/" +taskId)
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
    }

}

