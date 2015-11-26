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
    private Date mDate;
    private SimpleDateFormat mFormattedDate;

    private boolean mTodoComplete;



    public Todo() {
        //Generate unique identifier
        mId = UUID.randomUUID();
        //Generate date
        mDate = new Date();
        mFormattedDate = new SimpleDateFormat("MMM-d-yyyy");

    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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
