package com.qacart.todo.apis;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {

    public static Response addTodo(Todo todo , String token) {

         return  given()
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

    }

    public static Response getTodoById(String taskId , String token) {

        return  given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get("/api/v1/tasks/" +taskId)
                .then()
                .log().all()
                .extract().response();

    }
    public static Response getAllTodos(String token) {

        return  given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();

    }
    public static Response deleteTodoById(String taskId , String token) {

        return  given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .delete("/api/v1/tasks/" +taskId)
                .then()
                .log().all()
                .extract().response();

    }


}
