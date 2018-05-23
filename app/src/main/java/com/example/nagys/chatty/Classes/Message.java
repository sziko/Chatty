package com.example.nagys.chatty.Classes;

/**
 * Created by szilardnagy on 28/02/2018.
 */

public class Message {

    /* class used for creating new message objects in firebase database*/

    private String user;
    private String message;

    public Message(String user, String message) {

         this.message = message;
         this.user = user;
    }

    public Message() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
