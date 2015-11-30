package com.example.danielgalarza.collabstation;

/**
 * Created by danielgalarza on 11/22/15.
 */
public class Chat {

    String name;
    String message;
    String mapURL;

    //Firebase use this to map "message" and "name" to online database.
    public Chat() {
    }

    public Chat(String name, String message, String mapURL) {
        this.name = name;
        this.message = message;
        this.mapURL = mapURL;
    }

    public String getMapURL() {
        return mapURL;
    }

    public String getName(){
        return name;
    }

    public String getMessage(){
        return message;
    }
}
