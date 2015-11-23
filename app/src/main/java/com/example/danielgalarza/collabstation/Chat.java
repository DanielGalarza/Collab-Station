package com.example.danielgalarza.collabstation;

/**
 * Created by danielgalarza on 11/22/15.
 */
public class Chat {

    String name;
    String message;

    //Firebase use this to map "message" and "name" to online database.
    public Chat() {
    }

    public Chat(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName(){
        return name;
    }

    public String getMessage(){
        return message;
    }
}
