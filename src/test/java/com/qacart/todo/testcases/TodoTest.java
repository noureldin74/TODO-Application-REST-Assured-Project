package com.qacart.todo.testcases;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.ErrorMessages;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TodoTest {



    String token = UserSteps.getUserToken();

    @Test
    public void shouldBeAbleToCreateTodo() {

        Todo todo = TodoSteps.generateTodo();
        Response response = TodoApi.addTodo(todo, token);
        Todo createdTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(createdTodo.getCompleted(), equalTo(todo.getCompleted()));
        assertThat(createdTodo.getItem(), equalTo(todo.getItem()));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutAuth() {
        Todo todo = TodoSteps.generateTodo();
        Response response = TodoApi.addTodo(todo, "");
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(errorMessages.getMessage(), equalTo("Unauthorized, please insert a correct token"));
    }

    @Test
    public void shouldNotBeAbleToCreateTodoWithoutItem() {
        Todo todo = TodoSteps.generateTodo();
        todo.setItem(null);
        Response response = TodoApi.addTodo(todo, token);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"item\" is required"));
    }

    @Test
    public void shouldnotbeabletocreatetodowithoutiscompleted() {
        Todo todo = TodoSteps.generateTodo();
        todo.setCompleted(null);
        Response response = TodoApi.addTodo(todo, token);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"isCompleted\" is required"));
    }


    @Test
    public void getTodoById() {
        String todoId = TodoSteps.getTodoById(token);
        Response response = TodoApi.getTodoById(todoId, token);

        Todo todo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(todo.getId(), equalTo(todoId));

    }

    @Test
    public void getAllTodos() {
        Response response = TodoApi.getAllTodos(token);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void deleteTodoById() {
        String todoId = TodoSteps.getTodoById(token);
        Response response = TodoApi.deleteTodoById(todoId, token);
        assertThat(response.statusCode(), equalTo(200));
    }

}

