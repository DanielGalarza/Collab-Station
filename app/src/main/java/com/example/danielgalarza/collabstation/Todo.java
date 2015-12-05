package com.example.danielgalarza.collabstation;

/**
 * Created by dustin on 11/25/15.
 */
public class Todo {

    String title;
    String description;
    String date;
    boolean todoComplete;

    public Todo() {
    }

    public Todo(String title, String date, String description, Boolean todoComplete) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.todoComplete = todoComplete;
    }

    public boolean isTodoComplete() {
        return todoComplete;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }



}
