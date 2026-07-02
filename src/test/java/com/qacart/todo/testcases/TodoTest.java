package com.qacart.todo.testcases;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.ErrorMessages;
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
        Response response = TodoApi.addTodo(todo, token);
        Todo createdTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(createdTodo.getCompleted(), equalTo(todo.getCompleted()));
        assertThat(createdTodo.getItem(), equalTo(todo.getItem()));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutAuth() {
        Todo todo = new Todo(false, "Learn Appium");
        Response response = TodoApi.addTodo(todo, "");
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(errorMessages.getMessage(), equalTo("Unauthorized, please insert a correct token"));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutItem() {
        Todo todo = new Todo(false, null);
        Response response = TodoApi.addTodo(todo, token);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"item\" is required"));
    }

    @Test
    public void shouldnotbeabletocreatetodowithoutiscompleted() {
        Todo todo = new Todo(null, "Learn Appium");
        Response response = TodoApi.addTodo(todo, token);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"isCompleted\" is required"));
    }


    @Test
    public void getTodoById() {
        String taskId = "6a46cf016e3bf3001529a2a3";
        Response response = TodoApi.getTodoById(taskId, token);

        Todo todo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(todo.getId(), equalTo(taskId));

    }

    @Test
    public void getAllTodos() {
        Response response = TodoApi.getAllTodos(token);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void deleteTodoById() {
        String taskId = "6a46cf016e3bf3001529a2a3";

        Response response = TodoApi.deleteTodoById(taskId, token);
        assertThat(response.statusCode(), equalTo(200));
    }

}

