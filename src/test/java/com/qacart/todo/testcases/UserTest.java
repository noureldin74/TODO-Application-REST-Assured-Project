package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.ErrorMessages;
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
        User user = new User("Nour", "Ahmed", "kola44464589@example.com", "12345678");
        Response response = UserApi.register(user);
        User registeredUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(registeredUser.getFirstName(), equalTo(user.getFirstName()));
    }
    @Test
    public void shouldNotBeAbleToRegisterWithExistingEmail() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "12345678");
        Response response = UserApi.register(user);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("Email is already exists in the Database"));

    }


    @Test
    public void registerWithInvalidPasswordLength() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "123456");

        Response response=UserApi.register(user);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"password\" length must be at least 8 characters long"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithEmptyEmail() {
        User user = new User("Nour", "Ahmed", "", "12345678");
        Response response = UserApi.register(user);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"email\" is not allowed to be empty"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithEmptyPassword() {
        User user = new User("Nour", "Ahmed", "kola@example.com", "");
        Response response=UserApi.register(user);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(errorMessages.getMessage(), equalTo("\"password\" is not allowed to be empty"));
    }


    // login user test cases

    @Test
     public void LoginWithValidCredentials () {
        User user = new User("kola@example.com", "12345678");
        Response response = UserApi.login(user);
        User registeredUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(registeredUser.getFirstName(), equalTo("Nour"));
        assertThat(registeredUser.getAccessToken(), not(equalTo(null)));



        }


     @Test
     public void LoginWithInvalidCredentials () {
        User user = new User("kola@example.com", "wrongpassword");
        Response response = UserApi.login(user);
        ErrorMessages errorMessages = response.body().as(ErrorMessages.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(errorMessages.getMessage(), equalTo("The email and password combination is not correct, please fill a correct email and password"));


        }


    }