package com.qacart.todo.base;

import com.qacart.todo.data.Route;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static RequestSpecification GetRequestSpec(){
        return given()
                .baseUri(Route.BASE_URI)
                .contentType(ContentType.JSON);
    }


}
