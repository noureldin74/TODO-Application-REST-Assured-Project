package com.qacart.todo.data;

public class Route {

// constants for API routes

    public static final String BASE_URI = "https://qacart-todo.herokuapp.com";
    public static final String REGISTER = "/api/v1/users/register";
    public static final String LOGIN = "/api/v1/users/login";
    public static final String ADD_TODO = "/api/v1/tasks";
    public static final String GET_TODO_BY_ID = "/api/v1/tasks/{taskId}";
    public static final String GET_ALL_TODOS = "/api/v1/tasks";
    public static final String DELETE_TODO_BY_ID = "/api/v1/tasks/{taskId}";
}
