package com.qacart.todo.data;

public class Errors {

    public static final String ExistingEmail = "Email is already exists in the Database";
    public static final String PASSWORD_LENGTH = "\"password\" length must be at least 8 characters long";
    public static final String EMPTY_EMAIL = "\"email\" is not allowed to be empty";
    public static final String EMPTY_PASSWORD = "\"password\" is not allowed to be empty";
    public static final String UNAUTHORIZED = "The email and password combination is not correct, please fill a correct email and password";
    public static final String MISSING_TOKEN = "Unauthorized, please insert a correct token";
    public static final String EMPTY_ITEM = "\"item\" is required";
    public static final String EMPTY_COMPLETED = "\"isCompleted\" is required";
}
