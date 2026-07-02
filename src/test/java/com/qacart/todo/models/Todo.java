package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)  //not include null values in the json serialization

public class Todo {

    //fields
    @JsonProperty("isCompleted")
    private Boolean isCompleted;
    @JsonProperty("_id")
    private String id;
    private String item;
    private String userID;
    private String createdAt;
    @JsonProperty("__v")
    private Integer v;

    //constructor
    public Todo() {
    }
    public Todo(Boolean isCompleted, String item) {
        this.isCompleted = isCompleted;
        this.item = item;
    }


     //setters and getters
     @JsonProperty("isCompleted")
    public Boolean getCompleted() {
        return isCompleted;
    }
    @JsonProperty("isCompleted")
    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }


    public String getItem() {
        return item;
    }


    public void setItem(String item) {
        this.item = item;
    }


    public String getUserID() {
        return userID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @JsonProperty("__v")

    public Integer getV() {
        return v;
    }
    @JsonProperty("__v")

    public void setV(Integer v) {
        this.v = v;
    }





















}
