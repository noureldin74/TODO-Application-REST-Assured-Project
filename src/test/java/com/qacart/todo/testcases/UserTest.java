package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {


// register user test cases
    @Test
    public void shouldBeAbleToRegister() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "12345678");

        Response response= given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("firstName"), equalTo("Nour"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithExistingEmail() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "12345678");

        Response response=given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("Email is already exists in the Database"));

    }


    @Test
    public void registerWithInvalidPasswordLength() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "123456");

        Response response=given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("\"password\" length must be at least 8 characters long"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithEmptyEmail() {
        User user = new User("Nour", "Ahmed", "", "12345678");

        Response response=given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("\"email\" is not allowed to be empty"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithEmptyPassword() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("\"password\" is not allowed to be empty"));
    }


    // login user test cases

    @Test
     public void LoginWithValidCredentials () {
        User user = new User("kola@example.com", "12345678");
        Response response = given()
                    .baseUri("https://qacart-todo.herokuapp.com")
                    .contentType(ContentType.JSON)
                    .body(user)
                    .when()
                    .post("/api/v1/users/login")
                    .then()
                    .log().all()
                    .extract().response();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("firstName"), equalTo("Nour"));
        assertThat(response.path("access_token"), not(equalTo(null)));



        }


     @Test
     public void LoginWithInvalidCredentials () {
         User user = new User("kola@example.com", "wrongpassword");

         Response response = given()
                    .baseUri("https://qacart-todo.herokuapp.com")
                    .contentType(ContentType.JSON)
                    .body(user)
                    .when()
                    .post("/api/v1/users/login")
                    .then()
                    .log().all()
                    .extract().response();

        assertThat(response.statusCode(), equalTo(401));
        assertThat(response.path("message"), equalTo("The email and password combination is not correct, please fill a correct email and password"));


        }


    }