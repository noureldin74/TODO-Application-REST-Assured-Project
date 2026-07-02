package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.User;

public class UserSteps {

    public static User generateUser() {

        Faker  faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "12345678";
        return new User(firstName, lastName, email, password);

    }
    public static User getregisteredUser() {
        User user = generateUser();
        UserApi.register(user);
        return user;



    }




}
