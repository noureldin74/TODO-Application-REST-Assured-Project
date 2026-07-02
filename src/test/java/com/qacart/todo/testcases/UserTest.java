package com.qacart.todo.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {


// register user test cases
    @Test
    public void shouldBeAbleToRegister() {

        String body = "{\n" +
                "  \"firstName\": \"Nour\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"email\": \"testooly@example.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName", equalTo("Nour"));


    }

    @Test
    public void shouldNotBeAbleToRegisterWithExistingEmail() {
        String body = "{\n" +
                "  \"firstName\": \"Nour\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"email\": \"testkola@example.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }


    @Test
    public void registerWithInvalidPasswordLength() {
        String body = "{\n" +
                "  \"firstName\": \"Nour\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"email\": \"testkola@example.com\",\n" +
                "  \"password\": \"123456\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("\"password\" length must be at least 8 characters long"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithEmptyEmail() {
        String body = "{\n" +
                "  \"firstName\": \"Nour\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"email\": \"\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("\"email\" is not allowed to be empty"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithEmptyPassword() {
        String body = "{\n" +
                "  \"firstName\": \"Nour\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"email\": \"testkola@example.com\",\n" +
                "  \"password\": \"\"\n" +
                "}";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("\"password\" is not allowed to be empty"));
    }




    // login user test cases

    @Test
     public void LoginWithValidCredentials () {

            String body = "{\n" +
                    "  \"email\": \"testooly@example.com\",\n" +
                    "  \"password\": \"12345678\"\n" +
                    "}";

            given()
                    .baseUri("https://qacart-todo.herokuapp.com")
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post("/api/v1/users/login")
                    .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .assertThat().body("firstName", equalTo("Nour"))
                    .assertThat().body("access_token", not(equalTo(null)));



        }


     @Test
     public void LoginWithInvalidCredentials () {

            String body = "{\n" +
                    "  \"email\": \"testooly@example.com\",\n" +
                    "  \"password\": \"wrongpassword\"\n" +
                    "}";

            given()
                    .baseUri("https://qacart-todo.herokuapp.com")
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post("/api/v1/users/login")
                    .then()
                    .log().all()
                    .assertThat().statusCode(401)
                    .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password"));


        }


    }