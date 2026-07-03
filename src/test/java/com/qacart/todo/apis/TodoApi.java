package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {

    public static Response addTodo(Todo todo , String token) {

         return  given()
                .spec(Specs.GetRequestSpec())
                .auth().oauth2(token)
                .body(todo)
                .when()
                .post(Route.ADD_TODO)
                .then()
                .log().all()
                .extract().response();

    }

    public static Response getTodoById(String taskId , String token) {

        return  given()
                .spec(Specs.GetRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.GET_TODO_BY_ID.replace("{taskId}", taskId))
                .then()
                .log().all()
                .extract().response();

    }

    public static Response getAllTodos(String token) {

        return  given()
                .spec(Specs.GetRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.GET_ALL_TODOS)
                .then()
                .log().all()
                .extract().response();

    }

    public static Response deleteTodoById(String taskId , String token) {

        return  given()
                .spec(Specs.GetRequestSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.DELETE_TODO_BY_ID.replace("{taskId}", taskId))
                .then()
                .log().all()
                .extract().response();

    }



}
