package com.example.danielgalarza.collabstation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dustin on 11/25/15.
 */
public class Todo {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private SimpleDateFormat mFormattedDate;
    private boolean mTodoComplete;


    public Todo() {
    }


    public Todo(String dummy) {

        mId = UUID.randomUUID();           //Generate unique identifier
        mDate = new Date();                //Generate date
        mFormattedDate = new SimpleDateFormat("MMM-d-yyyy");
        mTitle = "";
        mDescription = "";


        //mTodoComplete = false;

    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isTodoComplete() {
        return mTodoComplete;
    }

    public void setTodoComplete(boolean todoComplete) {
        mTodoComplete = todoComplete;
    }

}
